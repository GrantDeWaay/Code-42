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

/**
 * Controller for course endpoints <p>
 * HTTP 200 = good request. <p>
 * HTTP 202 = good request and data has been changed. <p>
 * HTTP 403 = incorrect permission. <p>
 * HTTP 404 = user or data not found. <p>
 */
@RestController
public class CourseController {

    @Autowired
    private CourseService cs;

    @Autowired
    private AssignmentService as;

    @Autowired
    private UserService us;

    /**
     * Get list of all courses.
     * <p>
     * Client must be teacher or admin.
     * <p>
     * Get request, path "/course".
     *
     * @param token permission token
     * @return HTTP response, list of all courses
     */
    @GetMapping("/course")
    public @ResponseBody ResponseEntity<List<ApiCourse>> getCourseList(@RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Course> result = cs.findAll();

        List<ApiCourse> courses = new LinkedList<>();

        for (Course c : result) {
            courses.add(new ApiCourse(c));
        }

        return new ResponseEntity<List<ApiCourse>>(courses, HttpStatus.OK);
    }

    /**
     * Get course by id.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/course/{id}".
     *
     * @param id    course's id
     * @param token permission token
     * @return HTTP response, course data
     */
    @GetMapping("/course/{id}")
    public @ResponseBody ResponseEntity<ApiCourse> getCourse(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Course> c = cs.findById(id);

        if (!c.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiCourse>(new ApiCourse(c.get()), HttpStatus.OK);
    }

    /**
     * Get list of assignments for a course.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/course/{id}/assignments".
     *
     * @param id    course's id
     * @param token permission token
     * @return HTTP response, assignment list
     */
    @GetMapping("/course/{id}/assignments")
    public @ResponseBody ResponseEntity<Set<ApiAssignment>> getCourseAssignmentList(@PathVariable long id, @RequestParam String token) {
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

    /**
     * Get list of users enrolled in course.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/course/{id}/users".
     *
     * @param id    course's id
     * @param token permission token
     * @return HTTP response, list of users
     */
    @GetMapping("/course/{id}/users")
    public @ResponseBody ResponseEntity<Set<ApiUser>> getCourseUserList(@PathVariable long id, @RequestParam String token) {
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

    /**
     * Get list of students enrolled in course.
     * <p>
     * Get request, path "/course/{id}/students".
     *
     * @param id course's id
     * @return HTTP response, list of students
     */
    @GetMapping("/course/{id}/students")
    public @ResponseBody ResponseEntity<Set<ApiUser>> getCourseStudentList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiUser> users = new HashSet<>();

        Iterator<User> iter = result.get().getStudents().iterator();

        while (iter.hasNext()) {
            User u = iter.next();
            if (u.getType().equals("student")) {
                users.add(new ApiUser(u));
            }
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get list of teachers enrolled in course.
     * <p>
     * Get request, path "/course/{id}/teachers".
     *
     * @param id course's id
     * @return HTTP response, list of teachers
     */
    @GetMapping("/course/{id}/teachers")
    public @ResponseBody ResponseEntity<Set<ApiUser>> getCourseTeacherList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiUser> users = new HashSet<>();

        Iterator<User> iter = result.get().getStudents().iterator();

        while (iter.hasNext()) {
            User u = iter.next();
            if (u.getType().equals("teacher")) {
                users.add(new ApiUser(u));
            }
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get list of admins enrolled in course.
     * <p>
     * Get request, path "/course/{id}/admins".
     *
     * @param id course's id
     * @return HTTP response, list of admins
     */
    @GetMapping("/course/{id}/admins")
    public @ResponseBody ResponseEntity<Set<ApiUser>> getCourseAdminList(@PathVariable long id) {
        Optional<Course> result = cs.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiUser> users = new HashSet<>();

        Iterator<User> iter = result.get().getStudents().iterator();

        while (iter.hasNext()) {
            User u = iter.next();
            if (u.getType().equals("admin")) {
                users.add(new ApiUser(u));
            }
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Create a new courses.
     * <p>
     * Client must be admin.
     * <p>
     * Post request, path "/course/create".
     *
     * @param c     course data
     * @param token permission token
     * @return HTTP response, course data with generated id
     */
    @PostMapping("/course/create")
    public @ResponseBody ResponseEntity<ApiCourse> createCourse(@RequestBody ApiCourse c, @RequestParam String token) {
        if (!UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        c.setCreationDate(Calendar.getInstance().getTime());

        Course course = new Course(c);
        cs.create(course);

        return new ResponseEntity<ApiCourse>(new ApiCourse(course), HttpStatus.OK);
    }

    /**
     * Update a course.
     * <p>
     * Client must be admin.
     * <p>
     * Put request, path "/course/{id}/update".
     *
     * @param id    course's id
     * @param c     course data
     * @param token permission token
     * @return HTTP response, course data
     */
    @PutMapping("/course/{id}/update")
    public @ResponseBody HttpStatus updateCourse(@PathVariable long id, @RequestBody ApiCourse c, @RequestParam String token) {
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

    /**
     * Delete an assignment.
     * <p>
     * Client must be admin.
     * <p>
     * Delete request, path "/course/{id}/delete".
     *
     * @param id    course's id
     * @param token permission token
     * @return HTTP response
     */
    @DeleteMapping("/course/{id}/delete")
    public @ResponseBody HttpStatus deleteAssignment(@PathVariable long id, @RequestParam String token) {
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

    /**
     * Get a list of upcoming assignments for a course.
     * <p>
     * Get request, path "/course/{id}/upcoming".
     *
     * @param id    course's id
     * @param token permission token
     * @return HTTP response, list of assignments
     */
    @GetMapping("/course/{id}/upcoming")
    public @ResponseBody ResponseEntity<Set<ApiAssignment>> getUpcomingAssignments(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Course> course = cs.findById(id);

        if (!course.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Date now = new Date();

        Set<ApiAssignment> upcomingAssignments = new HashSet<>();

        for (Assignment a : course.get().getAssignments()) {
            if (a.getDueDate().compareTo(now) < 0) {
                upcomingAssignments.add(new ApiAssignment(a));
            }
        }

        return new ResponseEntity<>(upcomingAssignments, HttpStatus.OK);
    }

    /**
     * Assign an assignment to a course.
     * <p>
     * Put request, path "/course/{courseId}/assignment/{assignmentId}".
     *
     * @param courseId     course's id
     * @param assignmentId assignment's id
     * @param token        permission token
     * @return HTTP response
     */
    @PutMapping("/course/{courseId}/assignment/{assignmentId}")
    public @ResponseBody HttpStatus addAssignmentToCourse(@PathVariable long courseId, @PathVariable long assignmentId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
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

    /**
     * Remove an assignment from a course.
     * <p>
     * Delete request, path "/course/{courseId}/assignment/{assignmentId}".
     *
     * @param courseId     course's id
     * @param assignmentId assignment's id
     * @param token        permission token
     * @return HTTP response
     */
    @DeleteMapping("/course/{courseId}/assignment/{assignmentId}")
    public @ResponseBody HttpStatus removeAssignmentFromCourse(@PathVariable long courseId, @PathVariable long assignmentId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
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

    /**
     * Add a user to a course.
     * <p>
     * Put request, path "/course/{courseId}/user/{userId}".
     *
     * @param courseId course's id
     * @param userId   user's id
     * @param token    permission token
     * @return HTTP response
     */
    @PutMapping("/course/{courseId}/user/{userId}")
    public @ResponseBody HttpStatus addUserToCourse(@PathVariable long courseId, @PathVariable long userId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
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

    /**
     * Remove a user from a course.
     * <p>
     * Delete request, path "/course/{courseId}/user/{userId}".
     *
     * @param courseId course's id
     * @param userId   user's id
     * @param token    permission token
     * @return HTTP response
     */
    @DeleteMapping("/course/{courseId}/user/{userId}")
    public @ResponseBody HttpStatus removeUserFromCourse(@PathVariable long courseId, @PathVariable long userId, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
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
