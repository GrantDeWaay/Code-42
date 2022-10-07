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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    CourseService cs;

    @GetMapping("/course")
    public @ResponseBody List<ApiCourse> getCourseList() {
        List<Course> result = cs.findAll();

        List<ApiCourse> courses = new LinkedList<>();

        for(Course c : result) {
            courses.add(new ApiCourse(c));
        }

        return courses;
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
    public @ResponseBody ApiCourse createCourse(@RequestBody ApiCourse c) {
        c.setCreationDate(Calendar.getInstance().getTime());

        Course course = new Course(c);
        cs.create(course);

        return c;
    }

    @PutMapping("/course/{id}/update")
    public @ResponseBody HttpStatus updateCourse(@PathVariable long id, @RequestBody ApiCourse c) {
        Optional<Course> optional = cs.findById(id);

        if(!optional.isPresent()) return HttpStatus.NOT_FOUND;

        Course course = optional.get();

        course.setTitle(c.getTitle());
        course.setDescription(c.getDescription());
        course.setLanguages(c.getLanguages());

        cs.update(course);

        return HttpStatus.ACCEPTED;
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
