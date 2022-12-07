package coms309.controller;

import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import coms309.api.dataobjects.*;

import java.util.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * Controller for user endpoints. <p>
 * HTTP 200 = good request. <p>
 * HTTP 202 = good request and data has been changed. <p>
 * HTTP 403 = incorrect permission. <p>
 * HTTP 404 = user or data not found. <p>
 */
@RestController
public class UserController {


    @Autowired
    private UserService us;

    /**
     * Get a list of all users.
     * <p>
     * Client must be teacher or admin.
     * <p>
     * Get request, path "/user".
     *
     * @param token permission token
     * @return HTTP response, list of users
     */
    @ApiOperation(value = "Get a list of all Users in the system", response = Iterable.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/user")
    public @ResponseBody ResponseEntity<List<ApiUser>> getUserList(@RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<User> result = us.findAll();

        List<ApiUser> users = new LinkedList<>();

        for (User u : result) {
            users.add(new ApiUser(u));
        }

        return new ResponseEntity<List<ApiUser>>(users, HttpStatus.OK);
    }

    /**
     * Get user data from id.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/user/{id}".
     *
     * @param id    user's id
     * @param token permission token
     * @return HTTP response, user data
     */
    @ApiOperation(value = "Get a User by ID", response = ApiUser.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/user/{id}")
    public @ResponseBody ResponseEntity<ApiUser> getUser(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> u = us.findById(id);

        if (!u.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiUser(u.get()), HttpStatus.OK);
    }

    @GetMapping("/user/get/{id}")
    public @ResponseBody ResponseEntity<ApiUser> getUserById(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> u = us.findById(id);
        if (!u.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiUser(), HttpStatus.OK);
    }

    /**
     * Get user data from email.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/user/{email}".
     *
     * @param email user's email
     * @return HTTP response, user data
     */
    @ApiOperation(value = "Get a User by email", response = ApiUser.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/user/{email}")
    public @ResponseBody ResponseEntity<ApiUser> getUserByEmail(@PathVariable String email) {
        Optional<User> u = us.findByEmail(email);

        if (!u.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiUser(u.get()), HttpStatus.OK);
    }

    /**
     * Get list of courses for a user.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/user/{id}/courses".
     *
     * @param id    user's id
     * @param token permission token
     * @return HTTP response, list of courses
     */
    @ApiOperation(value = "Get a list of all Courses a User is associated with", response = Iterable.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/user/{id}/courses")
    public @ResponseBody ResponseEntity<Set<ApiCourse>> getUserCourseList(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> result = us.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiCourse> courses = new HashSet<>();

        Iterator<Course> iter = result.get().getCourses().iterator();

        while (iter.hasNext()) {
            courses.add(new ApiCourse(iter.next()));
        }

        return new ResponseEntity<Set<ApiCourse>>(courses, HttpStatus.OK);
    }

    /**
     * Get list of grades for a user.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/user/{id}/grades".
     *
     * @param id    user's id
     * @param token permission token
     * @return HTTP response, list of grades
     */
    @ApiOperation(value = "Get a list of all Grades for a User", response = Iterable.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/user/{id}/grades")
    public @ResponseBody ResponseEntity<Set<ApiGrade>> getUserGradeList(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> result = us.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiGrade> grades = new HashSet<>();

        Iterator<Grade> iter = result.get().getGrades().iterator();

        while (iter.hasNext()) {
            grades.add(new ApiGrade(iter.next()));
        }

        return new ResponseEntity<Set<ApiGrade>>(grades, HttpStatus.OK);
    }

    /**
     * Get a users grade for a specific assignment.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/user/{userId}/assignment/{assignmentId}/grade".
     *
     * @param userId       user's id
     * @param assignmentId assignment's id
     * @param token        permission token
     * @return HTTP response, grade data
     */
    @ApiOperation(value = "Get a User's grade on an Assignment", response = ApiGrade.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/user/{userId}/assignment/{assignmentId}/grade")
    public @ResponseBody ResponseEntity<ApiGrade> getUserGrade(@PathVariable long userId, @PathVariable long assignmentId, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<User> user = us.findById(userId);

        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Iterator<Grade> iter = user.get().getGrades().iterator();

        while (iter.hasNext()) {
            Grade g = iter.next();

            if (g.getAssignment().getId() == assignmentId) {
                return new ResponseEntity<>(new ApiGrade(g), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create a user.
     * <p>
     * Client must be teacher or admin.
     * <p>
     * Post request, path "/user/create".
     *
     * @param u     user data
     * @param token permission token
     * @return HTTP response, user data with generated it
     */
    @ApiOperation(value = "Create a new User", response = ApiUser.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/user/create")
    public @ResponseBody ResponseEntity<ApiUser> createUser(@RequestBody ApiUser u, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        u.setCreationDate(Calendar.getInstance().getTime());

        User user = new User(u);
        us.create(user);

        return new ResponseEntity<ApiUser>(new ApiUser(user), HttpStatus.OK);
    }

    /**
     * Update a user.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Put request, path "/user/{id}/update".
     *
     * @param id    user's id
     * @param u     user data
     * @param token permission token
     * @return HTTP response
     */
    @ApiOperation(value = "Update an existing User", response = HttpStatus.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PutMapping("/user/{id}/update")
    public @ResponseBody HttpStatus updateUser(@PathVariable long id, @RequestBody ApiUser u, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<User> optional = us.findById(id);

        if (!optional.isPresent()) return HttpStatus.NOT_FOUND;

        User user = optional.get();

        if (!Objects.equals(u.getUsername(), "") && u.getUsername() != null) {
            user.setUsername(u.getUsername());
        }
        if (!Objects.equals(u.getFirstName(), "") && u.getFirstName() != null) {
            user.setFirstName(u.getFirstName());
        }
        if (!Objects.equals(u.getLastName(), "") && u.getLastName() != null) {
            user.setLastName(u.getLastName());
        }
        if (!Objects.equals(u.getPassword(), "") && u.getPassword() != null) {
            user.setPassword(u.getPassword());
        }
        if (!Objects.equals(u.getEmail(), "") && u.getEmail() != null) {
            user.setEmail(u.getEmail());
        }
        if (!Objects.equals(u.getType(), "") && u.getType() != null) {
            user.setType(u.getType());
        }

        us.update(user);

        return HttpStatus.ACCEPTED;
    }

    /**
     * Delete a user.
     * <p>
     * Client must be teacher or admin.
     * <p>
     * Delete request, path "/user/{id}/delete".
     *
     * @param id    user's id
     * @param token permission token
     * @return HTTP response
     */
    @ApiOperation(value = "Delete a User from the system", response = HttpStatus.class, tags = "user-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @DeleteMapping("/user/{id}/delete")
    public @ResponseBody HttpStatus deleteUser(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<User> u = us.findById(id);
        if (u.isPresent()) {
            us.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

}
