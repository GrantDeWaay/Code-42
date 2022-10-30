package coms309.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.coderunner.CRunner;
import coms309.coderunner.TempFileManager;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.services.AssignmentService;

@RestController
public class CodeRunnerController {

    @Autowired
    private AssignmentService as;

    private TempFileManager tempFileManager = new TempFileManager("/home/gitlab-runner/tempfiles/users/");
    
    @PutMapping("/run/{assignmentId}")
    public ResponseEntity<String> runAssignment(@PathVariable long assignmentId, @RequestBody ApiCodeSubmission codeSubmission, @RequestParam String token) {
        if(!UserTokens.isStudent(token)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Long studentId = UserTokens.getID(token);

        Optional<Assignment> a = as.findById(assignmentId);

        if(!a.isPresent()) return new ResponseEntity<>("Assignment not found", HttpStatus.NOT_FOUND);

        AssignmentFile af = a.get().getAssignmentFile();

        if(af == null) return new ResponseEntity<>("No file mapping found for assignment", HttpStatus.NOT_FOUND);

        tempFileManager.createAssignmentFolder(studentId, assignmentId);

        System.out.println("Past folder creation");

        try {
            CRunner runner = new CRunner(af.getCodeFolder(), tempFileManager.getAssignmentFolderPath(studentId, assignmentId), codeSubmission.getName());

            System.out.println("Past runner creation");

            // TODO copy over body of this request as a file to be executed
            File codeFile = new File(tempFileManager.getAssignmentFolderPath(studentId, assignmentId) + "/" + codeSubmission.getName());

            FileWriter writer = new FileWriter(codeFile);

            System.out.println("Past writer creation");

            writer.write(codeSubmission.getContents());

            writer.close();

            System.out.println("Past content copy");

            if(!runner.compile()) {
                return new ResponseEntity<>("Compilation failed", HttpStatus.ACCEPTED);
            }

            System.out.println("Past compilation");

            if(!runner.run()) {
                return new ResponseEntity<>("Run failed", HttpStatus.ACCEPTED);
            }

            System.out.println("Past execution");
        // TODO make this better
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

}
