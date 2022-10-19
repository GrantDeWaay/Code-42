package coms309.controller;

import coms309.api.dataobjects.ApiAssignment;
import coms309.api.dataobjects.ApiCourse;
import coms309.api.dataobjects.ApiUser;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import coms309.database.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    @Autowired
    CourseService cs;

    @GetMapping("/course")
    public @ResponseBody
    ResponseEntity<List<ApiCourse>> getCourseList(@RequestParam String token) {
        if (UserTokens.isTeacher(token) || UserTokens.isAdmin(token)) {

            List<Course> result = cs.findAll();

            List<ApiCourse> courses = new LinkedList<>();

            for (Course c : result) {
                courses.add(new ApiCourse(c));
            }

            return new ResponseEntity<List<ApiCourse>>(courses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/course/{id}")
    public @ResponseBody
    ResponseEntity<ApiCourse> getCourse(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<Course> c = cs.findById(id);

            if (!c.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<ApiCourse>(new ApiCourse(c.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/course/{id}/assignments")
    public @ResponseBody
    ResponseEntity<Set<ApiAssignment>> getCourseAssignmentList(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<Course> result = cs.findById(id);

            if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Set<ApiAssignment> assignments = new HashSet<>();

            Iterator<Assignment> iter = result.get().getAssignments().iterator();

            while (iter.hasNext()) {
                assignments.add(new ApiAssignment(iter.next()));
            }

            return new ResponseEntity<Set<ApiAssignment>>(assignments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/course/{id}/users")
    public @ResponseBody
    ResponseEntity<Set<ApiUser>> getCourseUserList(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<Course> result = cs.findById(id);

            if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Set<ApiUser> users = new HashSet<>();

            Iterator<User> iter = result.get().getStudents().iterator();

            while (iter.hasNext()) {
                users.add(new ApiUser(iter.next()));
            }

            return new ResponseEntity<Set<ApiUser>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/course/create")
    public @ResponseBody
    ResponseEntity<ApiCourse> createCourse(@RequestBody ApiCourse c, @RequestParam String token) {
        if (UserTokens.isAdmin(token)) {
            c.setCreationDate(Calendar.getInstance().getTime());

            Course course = new Course(c);
            cs.create(course);

            return new ResponseEntity<ApiCourse>(new ApiCourse(course), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/course/{id}/update")
    public @ResponseBody
    HttpStatus updateCourse(@PathVariable long id, @RequestBody ApiCourse c, @RequestParam String token) {
        if (UserTokens.isAdmin(token)) {
            Optional<Course> optional = cs.findById(id);

            if (!optional.isPresent()) return HttpStatus.NOT_FOUND;

            Course course = optional.get();

            course.setTitle(c.getTitle());
            course.setDescription(c.getDescription());
            course.setLanguages(c.getLanguages());

            cs.update(course);

            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @DeleteMapping("/course/{id}/delete")
    public @ResponseBody
    HttpStatus deleteAssignment(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isAdmin(token)) {
            Optional<Course> c = cs.findById(id);
            if (c.isPresent()) {
                cs.delete(id);
                return HttpStatus.ACCEPTED;
            } else {
                return HttpStatus.BAD_REQUEST;
            }
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

}
