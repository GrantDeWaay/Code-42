package coms309.controller;

import coms309.api.dataobjects.ApiAssignment;
import coms309.api.dataobjects.ApiCourse;
import coms309.api.dataobjects.ApiUser;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import coms309.database.services.AssignmentService;
import coms309.database.services.CourseService;
import coms309.database.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    @Autowired
    CourseService cs;

    @Autowired
    AssignmentService as;

    @Autowired
    UserService us;

    @GetMapping("/course")
    public @ResponseBody
    ResponseEntity<List<ApiCourse>> getCourseList(@RequestParam String token) {
        if (!UserTokens.isTeacher(token) || !UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Course> result = cs.findAll();

        List<ApiCourse> courses = new LinkedList<>();

        for (Course c : result) {
            courses.add(new ApiCourse(c));
        }

        return new ResponseEntity<List<ApiCourse>>(courses, HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    public @ResponseBody
    ResponseEntity<ApiCourse> getCourse(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Course> c = cs.findById(id);

        if (!c.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiCourse>(new ApiCourse(c.get()), HttpStatus.OK);
    }

    @GetMapping("/course/{id}/assignments")
    public @ResponseBody
    ResponseEntity<Set<ApiAssignment>> getCourseAssignmentList(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Course> result = cs.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiAssignment> assignments = new HashSet<>();

        Iterator<Assignment> iter = result.get().getAssignments().iterator();

        while (iter.hasNext()) {
            assignments.add(new ApiAssignment(iter.next()));
        }

        return new ResponseEntity<Set<ApiAssignment>>(assignments, HttpStatus.OK);
    }

    @GetMapping("/course/{id}/users")
    public @ResponseBody
    ResponseEntity<Set<ApiUser>> getCourseUserList(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Course> result = cs.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiUser> users = new HashSet<>();

        Iterator<User> iter = result.get().getStudents().iterator();

        while (iter.hasNext()) {
            users.add(new ApiUser(iter.next()));
        }

        return new ResponseEntity<Set<ApiUser>>(users, HttpStatus.OK);
    }

    @PostMapping("/course/create")
    public @ResponseBody
    ResponseEntity<ApiCourse> createCourse(@RequestBody ApiCourse c, @RequestParam String token) {
        if (!UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        c.setCreationDate(Calendar.getInstance().getTime());

        Course course = new Course(c);
        cs.create(course);

        return new ResponseEntity<ApiCourse>(new ApiCourse(course), HttpStatus.OK);
    }

    @PutMapping("/course/{id}/update")
    public @ResponseBody
    HttpStatus updateCourse(@PathVariable long id, @RequestBody ApiCourse c, @RequestParam String token) {
        if (!UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> optional = cs.findById(id);

        if (!optional.isPresent()) return HttpStatus.NOT_FOUND;

        Course course = optional.get();

        course.setTitle(c.getTitle());
        course.setDescription(c.getDescription());
        course.setLanguages(c.getLanguages());

        cs.update(course);

        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/course/{id}/delete")
    public @ResponseBody
    HttpStatus deleteAssignment(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> c = cs.findById(id);
        if (c.isPresent()) {
            cs.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping("/course/{courseId}/assignment/{assignmentId}")
    public @ResponseBody HttpStatus addAssignmentToCourse(@PathVariable long courseId, @PathVariable long assignmentId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) || !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> c = cs.findById(courseId);
        Optional<Assignment> a = as.findById(assignmentId);

        if (!c.isPresent() || !a.isPresent()) return HttpStatus.NOT_FOUND;

        c.get().getAssignments().add(a.get());
        cs.update(c.get());

        a.get().setCourse(c.get());
        as.update(a.get());

        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/course/{courseId}/assignment/{assignmentId}")
    public @ResponseBody HttpStatus removeAssignmentFromCourse(@PathVariable long courseId, @PathVariable long assignmentId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) || !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> c = cs.findById(courseId);
        Optional<Assignment> a = as.findById(assignmentId);

        if (!c.isPresent() || !a.isPresent()) return HttpStatus.NOT_FOUND;

        Course course = c.get();
        Assignment assignment = a.get();

        if (!course.getAssignments().contains(assignment) || assignment.getCourse() != course)
            return HttpStatus.NOT_FOUND;

        assignment.setCourse(null);
        course.getAssignments().remove(assignment);

        as.update(assignment);
        cs.update(course);

        return HttpStatus.ACCEPTED;
    }

    @PutMapping("/course/{courseId}/user/{userId}")
    public @ResponseBody HttpStatus addUserToCourse(@PathVariable long courseId, @PathVariable long userId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) || !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> c = cs.findById(courseId);
        Optional<User> u = us.findById(userId);

        if (!c.isPresent() || !u.isPresent()) return HttpStatus.NOT_FOUND;

        c.get().getStudents().add(u.get());
        cs.update(c.get());

        u.get().getCourses().add(c.get());
        us.update(u.get());

        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/course/{courseId}/user/{userId}")
    public @ResponseBody HttpStatus removeUserFromCourse(@PathVariable long courseId, @PathVariable long userId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) || !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> c = cs.findById(courseId);
        Optional<User> u = us.findById(userId);

        if (!c.isPresent() || !u.isPresent()) return HttpStatus.NOT_FOUND;

        Course course = c.get();
        User user = u.get();

        if (!course.getStudents().contains(user) || !user.getCourses().contains(course)) return HttpStatus.NOT_FOUND;

        course.getStudents().remove(user);
        user.getCourses().remove(course);

        cs.update(course);
        us.update(user);

        return HttpStatus.ACCEPTED;
    }

}
