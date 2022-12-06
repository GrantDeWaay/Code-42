package coms309.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms309.api.dataobjects.ApiCodeRunResult;
import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.coderunner.AssignmentUnitTestResult;
import coms309.coderunner.CodeRunner;
import coms309.coderunner.CodeRunnerFactory;
import coms309.coderunner.TempFileManager;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.dataobjects.AssignmentUnitTest;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.AssignmentService;
import coms309.database.services.GradeService;
import coms309.database.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for running code on the backend.
 */
@RestController
public class CodeRunnerController {

    @Autowired
    private AssignmentService as;

    @Autowired
    private GradeService gs;

    @Autowired
    private UserService us;

    /**
     * Send code to backend where it is compiled and ran.
     * Put request.
     * Path "/run/{assignmentId}".
     *
     * @param assignmentId   assignment's id
     * @param codeSubmission code submission data
     * @param token          permission token
     * @return code run result data
     */
    @ApiOperation(value = "Run a program for an assignment on the backend and grade it", response = ApiCodeRunResult.class, tags = "code-runner-controller")
    @ApiResponses(value = {
        @ApiResponse(code = 202, message = "ACCEPTED"),
        @ApiResponse(code = 403, message = "FORBIDDEN"),
        @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PutMapping("/run/{assignmentId}")
    public ResponseEntity<Iterable<AssignmentUnitTestResult>> runAssignment(@PathVariable long assignmentId, @RequestBody ApiCodeSubmission codeSubmission, @RequestParam String token) {
        if (!UserTokens.isStudent(token)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Long studentId = UserTokens.getID(token);

        Optional<User> u = us.findById(studentId);

        Optional<Assignment> a = as.findById(assignmentId);

        if (!a.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AssignmentFile af = a.get().getAssignmentFile();

        Set<AssignmentUnitTest> unitTests = a.get().getUnitTests();

        if (af == null) af = new AssignmentFile(); // initialize to empty

        // TODO move this stuff to a service of some kind??
        TempFileManager tempFileManager = new TempFileManager("/home/gitlab-runner/tempfiles/users/", studentId, a.get().getId());
        tempFileManager.createAssignmentFolder();

        try {
            CodeRunnerFactory factory = new CodeRunnerFactory();
            CodeRunner runner = factory.createCodeRunner(af, codeSubmission, tempFileManager, unitTests);

            File codeFile = new File(tempFileManager.getAssignmentFolderPath() + "/" + codeSubmission.getName());

            FileWriter writer = new FileWriter(codeFile);

            writer.write(codeSubmission.getContents());

            writer.close();

            if (runner.isCompiledRunner()) {
                if (!runner.compile()) {
                    AssignmentUnitTestResult result = new AssignmentUnitTestResult(null, "", "Compilation failed", false);
                    List<AssignmentUnitTestResult> results = new LinkedList<>();
                    results.add(result);
                    return new ResponseEntity<>(results, HttpStatus.ACCEPTED);
                }
            }

            List<AssignmentUnitTestResult> results = runner.run();

            double testCount = (double) results.size();

            double passCount = 0.0d;

            for (AssignmentUnitTestResult autr : results) {
                if(autr.isPassed()) passCount += 1.0d;
            }

            double finalGrade = (passCount / testCount) * 100.0d;

            Grade g = gs.findByUserAndAssignment(studentId, assignmentId);

            // if a grade for this assignment doesn't exist, create it
            // otherwise update the grade with the new grade and set the update time to now
            if(g == null) {
                g = new Grade(finalGrade, Calendar.getInstance().getTime());
                g.setAssignment(a.get());
                a.get().getGrades().add(g);
                g.setUser(u.get());
                u.get().getGrades().add(g);

                gs.create(g);
            } else {
                g.setGrade(finalGrade);
                g.setUpdateDate(Calendar.getInstance().getTime());

                gs.update(g);
            }

            return new ResponseEntity<>(results, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
