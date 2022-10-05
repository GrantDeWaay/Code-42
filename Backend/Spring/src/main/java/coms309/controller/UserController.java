package coms309.controller;

import coms309.controller.generator.LongGen;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService ur;

    @GetMapping("/user")
    public @ResponseBody List<User> getUserList() {
        return ur.findAll();
    }

    @GetMapping("/user/{id}")
    public @ResponseBody User getUser(@PathVariable long id) {
        Optional<User> u = ur.findById(id);
        return u.orElse(null);
        // Handle not valid input
    }

    @GetMapping("/user/{id}/courses")
    public @ResponseBody Course[] getUserCourseList(@PathVariable long id) {
        // TODO
        return null;
        // Handle not valid input
    }

    @GetMapping("/user/{id}/grades")
    public @ResponseBody Grade[] getUserGradeList(@PathVariable long id) {
        // TODO
        return null;
        // Handle not valid input
    }

    @PostMapping("/user/create")
    public @ResponseBody User createUser(@RequestBody User u) {
        u.setId(LongGen.generateId());
        ur.create(u);
        return u;
    }

    @PutMapping("/user/{id}/update")
    public @ResponseBody HttpStatus updateUser(@PathVariable long id, @RequestBody User u) {
        if (u.getId() == id){
            ur.update(u);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/user/{id}/delete")
    public @ResponseBody HttpStatus deleteUser() {
        return HttpStatus.ACCEPTED;
    }

}
