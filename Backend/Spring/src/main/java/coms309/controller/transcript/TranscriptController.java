package coms309.controller.transcript;

import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.GradeService;
import coms309.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.*;

/**
 * Controller to handle getting a student's transcript.
 */
@RestController
public class TranscriptController {
    @Autowired
    UserService us;

    @Autowired
    GradeService gs;

    /**
     * Gets a students transcript.
     * Includes student's courses, assignments, and grades.
     *
     * @param id    user's id
     * @param token user's token
     * @return HTTP response, transcript data
     */
    @ApiOperation(value = "Get a transcript of all Courses and Grades for a User", response = Transcript.class, tags = "transcript-controller")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),        
        @ApiResponse(code = 403, message = "FORBIDDEN"),
        @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/transcript/{id}")
    public ResponseEntity<Transcript> getStudentTranscript(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> user = us.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Transcript.CourseAssignments> ca = new LinkedList<>();
        for (Course c : user.get().getCourses()) {
            Set<Assignment> assignments = c.getAssignments(); // Assignments of specific course
            List<Transcript.AssignmentGrade> ag = new LinkedList<>();
            for (Assignment a : assignments) {
                Set<Grade> grades = a.getGrades(); // All grades from assignment
                for (Grade g : grades) {
                    ag.add(new Transcript.AssignmentGrade(a.getTitle(), g.getGrade()));
                }
            }
            ca.add(new Transcript.CourseAssignments(c.getTitle(), ag));
            ag.clear();
        }
        Transcript t = new Transcript(user.get().getFirstName() + " " + user.get().getLastName(), ca);
        return new ResponseEntity<Transcript>(t, HttpStatus.OK);
    }
}