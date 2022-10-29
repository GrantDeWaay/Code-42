package coms309.controller;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public HttpStatus runAssignment(@PathVariable long assignmentId, @RequestParam String token) {
        // TODO add authentication
        if(!UserTokens.isStudent(token)) return HttpStatus.FORBIDDEN;

        Long studentId = UserTokens.getID(token);

        Optional<Assignment> a = as.findById(assignmentId);

        if(!a.isPresent()) return HttpStatus.NOT_FOUND;

        AssignmentFile af = a.get().getAssignmentFile();

        // TODO this probably needs to be changed, since frontend should be able to distinguish bad assignment ids from files not existing (due to teacher issue)
        if(af == null) return HttpStatus.NOT_FOUND;

        tempFileManager.createAssignmentFolder(studentId, assignmentId);

        try {
            CRunner runner = new CRunner(af.getCodeFolder(), tempFileManager.getAssignmentFolderPath(studentId, assignmentId));

            runner.compile();
            runner.run();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.ACCEPTED;

    }

}
