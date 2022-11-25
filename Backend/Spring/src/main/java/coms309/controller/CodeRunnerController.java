package coms309.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Optional;

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
import coms309.coderunner.CodeRunner;
import coms309.coderunner.CodeRunnerFactory;
import coms309.coderunner.TempFileManager;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.AssignmentService;
import coms309.database.services.GradeService;
import coms309.database.services.UserService;

@RestController
public class CodeRunnerController {

    @Autowired
    private AssignmentService as;

    @Autowired
    private GradeService gs;

    @Autowired
    private UserService us;
    
    @PutMapping("/run/{assignmentId}")
    public ResponseEntity<ApiCodeRunResult> runAssignment(@PathVariable long assignmentId, @RequestBody ApiCodeSubmission codeSubmission, @RequestParam String token) {
        if(!UserTokens.isStudent(token)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Long studentId = UserTokens.getID(token);

        Optional<User> u = us.findById(studentId);

        Optional<Assignment> a = as.findById(assignmentId);

        if(!a.isPresent()) return new ResponseEntity<>(new ApiCodeRunResult(false, "Assignment not found", "", ""), HttpStatus.NOT_FOUND);

        AssignmentFile af = a.get().getAssignmentFile();

        if(af == null) af = new AssignmentFile(); // initialize to empty

        // TODO move this stuff to a service of some kind??
        TempFileManager tempFileManager = new TempFileManager("/home/gitlab-runner/tempfiles/users/", studentId, a.get().getId());
        tempFileManager.createAssignmentFolder();

        try {
            CodeRunnerFactory factory = new CodeRunnerFactory();
            CodeRunner runner = factory.createCodeRunner(af, codeSubmission, tempFileManager);

            File codeFile = new File(tempFileManager.getAssignmentFolderPath() + "/" + codeSubmission.getName());

            FileWriter writer = new FileWriter(codeFile);

            writer.write(codeSubmission.getContents());

            writer.close();

            if(runner.isCompiledRunner()) {
                if(!runner.compile()) {
                    return new ResponseEntity<>(new ApiCodeRunResult(false, "Compilation failed", "", runner.getStdErrData()), HttpStatus.ACCEPTED);
                }
            }

            if(!runner.run()) {
                return new ResponseEntity<>(new ApiCodeRunResult(false, "Run failed", "", ""), HttpStatus.ACCEPTED);
            }

            if(!runner.getStdOutData().equals(a.get().getExpectedOutput())) {
                Grade g = gs.findByUserAndAssignment(studentId, assignmentId);
                
                if(g == null) {
                    g = new Grade(0.0, Calendar.getInstance().getTime());
                    g.setAssignment(a.get());
                    a.get().getGrades().add(g);
                    g.setUser(u.get());
                    u.get().getGrades().add(g);

                    gs.create(g);
                }
                return new ResponseEntity<>(new ApiCodeRunResult(false, "Expected output differs", a.get().getExpectedOutput(), runner.getStdOutData()), HttpStatus.ACCEPTED);
            }

            Grade g = gs.findByUserAndAssignment(studentId, assignmentId);
            
            if(g == null) {
                g = new Grade(0.0, Calendar.getInstance().getTime());
                g.setAssignment(a.get());
                a.get().getGrades().add(g);
                g.setUser(u.get());
                u.get().getGrades().add(g);
            } else {
                g.setGrade(100.0);
                g.setUpdateDate(Calendar.getInstance().getTime());
            }
            
            gs.create(g);

            return new ResponseEntity<>(new ApiCodeRunResult(true, "Expected output matches", a.get().getExpectedOutput(), runner.getStdOutData()), HttpStatus.ACCEPTED);

        // TODO make this better
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
