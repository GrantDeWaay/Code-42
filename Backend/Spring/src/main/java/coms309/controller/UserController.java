package coms309.controller;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static coms309.controller.login.LoginController.sessionTokens;

@RestController
public class UserController {

    // Need to handle non valid tokens TODO

    @PutMapping("/user/password/{token}")
    public @ResponseBody HttpStatus changeUserPassword(@PathVariable String token) {
        long ID = sessionTokens.get(token);
        // Change the database
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/user")
    public @ResponseBody User[] getAllStudents() {
        User[] arr = null; // Get all users from table
        return arr;
    }

    @PutMapping("/user/create")
    public @ResponseBody HttpStatus createUser(@RequestBody User u) {
        // Add user to database
        return HttpStatus.ACCEPTED;
    }

}
