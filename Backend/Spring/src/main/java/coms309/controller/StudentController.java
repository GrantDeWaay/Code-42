package coms309.controller;

import coms309.controller.login.StudentLogin;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;

import java.util.HashMap;


@RestController
public class StudentController {

    HashMap<String, String> sessionTokens = new HashMap<String, String>();

    /*
     *  Login Controller
     *  Move to own file TODO
     */

    public String generateSessionToken(User u) {
        // Obviously this is not going to be real
        return "1234" + u.getUsername() + "5678";
    }


    public User getUserFromDatabase(User u){
        String username = u.getUsername();
        return null;
    }

    public User getUserFromDatabase(String token){
        String username = sessionTokens.get(token);
        return null;
    }

    @PostMapping("/login")
    public @ResponseBody String studentLogin(StudentLogin sl){
        User u = getUserFromDatabase(sl.getUsername());
        // Compare password and not username...
        // TODO
        // How to get hashed passwords from database in secure way
        if (u.getUsername().equals(sl.getUsername())){
            String token = sessionTokens.put(u.getUsername(),generateSessionToken(u));
            return "Login Success: " + token;
        } else {
            return "Username is not in database. Please sign up";
        }
    }

    /*
     *  General API for interacting with database
     */

    // Get

    @GetMapping("/student/{token}")
    public @ResponseBody User getUserData(String token) {
        return getUserFromDatabase(token);
    }

    @GetMapping("/student/courses/{token}")
    public @ResponseBody Course[] getUserCourses(String token){
        // Need to understand table relationships
        return null;
    }

    @GetMapping("/student/assignments/{token}")
    public @ResponseBody Assignment[] getUserAssignments(String token){
        // Need to understand table relationships
        return null;
    }

    // Post

    // Put

    // Delete

    // One more?

}
