package coms309.controller;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import coms309.database.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    CourseService cs;

    @GetMapping("/course")
    public @ResponseBody List<Course> getCourseList() {
        return cs.findAll();
    }

    @GetMapping("/course/{id}")
    public @ResponseBody Course getCourse(@PathVariable long id) {
        Optional<Course> c = cs.findById(id);
        return c.orElse(null);
        // Handle not valid input
    }

    @GetMapping("/course/{id}/assignments")
    public @ResponseBody ResponseEntity<Set<Assignment>> getCourseAssignmentList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get().getAssignments(), HttpStatus.OK);
    }

    @GetMapping("/course/{id}/users")
    public @ResponseBody ResponseEntity<Set<User>> getCourseUserList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get().getStudents(), HttpStatus.OK);
    }

    @PostMapping("/course/create")
    public @ResponseBody Course createCourse(@RequestBody Course c) {
        cs.create(c);
        return c;
    }

    @PutMapping("/course/{id}/update")
    public @ResponseBody HttpStatus updateCourse(@PathVariable long id, @RequestBody Course c) {
        if (c.getId() == id) {
            cs.update(c);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/course/{id}/delete")
    public @ResponseBody HttpStatus deleteAssignment(@PathVariable long id) {
        Optional<Course> c = cs.findById(id);
        if (c.isPresent()) {
            cs.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
