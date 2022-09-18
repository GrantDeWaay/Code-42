package coms309.controller;

import coms309.controller.login.StudentLogin;
import coms309.database.dataobjects.User;
import org.springframework.web.bind.annotation.*;



@RestController
public class StudentController {

    // TODO
    // When returning do we send session ID?
    // How will client know what data to get?

    public User getUserFromDatabase(StudentLogin sl){
        // Simulating getting user from database
        User u = new User();
        u.setUsername(sl.getUsername());
        // End of simulation, normally wouldn't have to set name...
        return u;
    }

    @PostMapping("/login")
    public @ResponseBody String studentLogin(StudentLogin sl){
        User u = getUserFromDatabase(sl);
        // Compare password and not username...
        // TODO
        // How to get hashed passwords from database in secure way
        if (u.getUsername().equals(sl.getUsername())){
            return "Login Success";
        } else {
            return "User name is database. Please sign up";
        }
    }

}
