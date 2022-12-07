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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

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
    @ApiOperation(value = "Get a list of all Courses in the system", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN")
    })
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
    @ApiOperation(value = "Get a Course by ID", response = ApiCourse.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Get a list of all Assignments for a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Get a list of all Users in a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Get a list of all Users of type student in a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Get a list of all Users of type teacher in a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Get a list of all Users of type admin in a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Create a new Course", response = ApiCourse.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Update an existing Course", response = HttpStatus.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PutMapping("/course/{id}/update")
    public @ResponseBody HttpStatus updateCourse(@PathVariable long id, @RequestBody ApiCourse c, @RequestParam String token) {
        if (!UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> optional = cs.findById(id);

        if (!optional.isPresent()) return HttpStatus.NOT_FOUND;

        Course course = optional.get();

        if (!Objects.equals(c.getTitle(), "") && c.getTitle() != null) {
            course.setTitle(c.getTitle());
        }
        if (!Objects.equals(c.getDescription(), "") && c.getDescription() != null) {
            course.setDescription(c.getDescription());
        }
        if (!Objects.equals(c.getLanguages(), "") && c.getLanguages() != null) {
            course.setLanguages(c.getLanguages());
        }

        cs.update(course);

        return HttpStatus.ACCEPTED;
    }

    /**
     * Delete a course.
     * <p>
     * Client must be admin.
     * <p>
     * Delete request, path "/course/{id}/delete".
     *
     * @param id    course's id
     * @param token permission token
     * @return HTTP response
     */
    @ApiOperation(value = "Delete a Course from the system", response = HttpStatus.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @DeleteMapping("/course/{id}/delete")
    public @ResponseBody HttpStatus deleteCourse(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Course> c = cs.findById(id);
        if (c.isPresent()) {
            cs.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
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
    @ApiOperation(value = "Get a list of all upcoming Assignments for a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Map an existing Assignment to a Course", response = HttpStatus.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Unmap an Assignment from a Course", response = HttpStatus.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Map an existing User to a Course", response = HttpStatus.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
    @ApiOperation(value = "Unmap a User from a Course", response = Iterable.class, tags = "course-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
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
