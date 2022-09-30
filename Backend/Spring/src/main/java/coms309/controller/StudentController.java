package coms309.controller;

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

    @GetMapping("/student/{token}")
    public @ResponseBody User getUserData(String token) {
        return null;
    }

    @GetMapping("/student/courses/{token}")
    public @ResponseBody Course[] getUserCourses(String token) {
        // Need to understand table relationships
        return null;
    }

    @GetMapping("/student/assignments/{token}")
    public @ResponseBody Assignment[] getUserAssignments(String token) {
        // Need to understand table relationships
        return null;
    }

    // Post

    // Put

    // Delete

    // One more?

}
