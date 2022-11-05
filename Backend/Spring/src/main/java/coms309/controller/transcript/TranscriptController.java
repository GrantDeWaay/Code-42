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

import java.util.*;

@RestController
public class TranscriptController {

    /*
        Input ID, and returns JSON with assignments and grades sorted by courses
     */

    @Autowired
    UserService us;

    @Autowired
    GradeService gs;

    @GetMapping("/transcript/{id}")
    public ResponseEntity<Transcript> getStudentTranscript(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> user = us.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Transcript.CourseAssignments> ca = new LinkedList<>(); // List of courses and their assignment-grade pairs
        Set<Grade> grades = user.get().getGrades();
        for (Course c : user.get().getCourses()) {
            List<Transcript.AssignmentGrade> ag = new LinkedList<>(); // New list of assignments and their grades for each course
            for (Grade g : grades){
                if (Objects.equals(c.getId(), g.getAssignment().getCourse().getId())) { // Only add assignment grades from assignments that belong to course
                    ag.add(new Transcript.AssignmentGrade(g.getAssignment().getTitle(), g.getGrade()));
                }
            }
            ca.add(new Transcript.CourseAssignments(c.getTitle(), ag));
            ag.clear();
        }
        Transcript t = new Transcript(user.get().getFirstName() + " " + user.get().getLastName(), ca);
        return new ResponseEntity<Transcript>(t, HttpStatus.OK);
    }
}