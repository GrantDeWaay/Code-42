package coms309.controller;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import coms309.database.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import coms309.api.dataobjects.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
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
    public @ResponseBody ResponseEntity<ApiCourse> getCourse(@PathVariable long id) {
        Optional<Course> c = cs.findById(id);

        if(!c.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiCourse(c.get()), HttpStatus.OK);
    }

    @GetMapping("/course/{id}/assignments")
    public @ResponseBody ResponseEntity<Set<ApiAssignment>> getCourseAssignmentList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiAssignment> assignments = new HashSet<>();

        Iterator<Assignment> iter = result.get().getAssignments().iterator();

        while(iter.hasNext()) {
            assignments.add(new ApiAssignment(iter.next()));
        }

        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping("/course/{id}/users")
    public @ResponseBody ResponseEntity<Set<ApiUser>> getCourseUserList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiUser> users = new HashSet<>();

        Iterator<User> iter = result.get().getStudents().iterator();

        while(iter.hasNext()) {
            users.add(new ApiUser(iter.next()));
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
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
