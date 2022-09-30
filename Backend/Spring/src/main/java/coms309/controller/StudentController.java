package coms309.controller;

import coms309.controller.login.LoginController;
import coms309.controller.login.StudentLogin;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    /*
     *  General API for interacting with database
     */

    // Get

    @GetMapping("/user/{token}")
    public @ResponseBody User getUserData(String token) {
        // Access Database
        return null;
    }

    @GetMapping("/user/courses/{token}")
    public @ResponseBody Course[] getUserCourses(String token) {
        // Access Database
        return null;
    }

    @GetMapping("/user/assignments/{token}")
    public @ResponseBody Assignment[] getUserAssignments(String token) {
        // Access Database
        return null;
    }

    // Post

    @PostMapping("/user/update{username}")
    public @ResponseBody String updateUserData(String username, @RequestBody User u) {
        // Access Database
        return "Success";
    }

    @PostMapping("/user/courses/add{username}")
    public @ResponseBody String addUserCourse(String username, @RequestBody Course[] c) {
        // Access Database
        return "Success";
    }

    @PostMapping("/user/assignments/add{username}")
    public @ResponseBody String addUserAssignment(String username, @RequestBody Assignment[] a) {
        // Access Database
        return "Success";
    }


    // Put

    // Delete

    @DeleteMapping("/user/delete{username}")
    public @ResponseBody String deleteUser(String username) {
        // Access Database
        LoginController.sessionTokens.remove(username);
        return "Success";
    }

    @DeleteMapping("/user/courses/delete{username}")
    public @ResponseBody String deleteUserCourse(String username, @RequestBody Course[] c) {
        // Access Database
        return "Success";
    }

    @DeleteMapping("/user/assignments/delete{username}")
    public @ResponseBody String deleteUserAssignment(String username, @RequestBody Assignment[] a) {
        // Access Database
        return "Success";
    }

}
