package coms309.controller;

import coms309.controller.generator.LongGen;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.Optional;

@RestController
public class UserController {

    /*
    Overall TODO
    Test all requests with Postman then SQL
    Implement missing methods if required for demo
    Add feature that handles invalid input
     */

    @Autowired
    UserService us;

    @GetMapping("/user")
    public @ResponseBody List<User> getUserList() {
        return us.findAll();
    }

    @GetMapping("/user/{id}")
    public @ResponseBody User getUser(@PathVariable long id) {
        Optional<User> u = us.findById(id);
        return u.orElse(null);
        // Handle not valid input
    }

    @GetMapping("/user/{id}/courses")
    public @ResponseBody ResponseEntity<Set<Course>> getUserCourseList(@PathVariable long id) {
        Optional<User> result = us.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get().getCourses(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}/grades")
    public @ResponseBody ResponseEntity<Set<Grade>> getUserGradeList(@PathVariable long id) {
        Optional<User> result = us.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Grade[] grades = new Grade[result.get().getGrades().size()];
        // result.get().getGrades().toArray(grades);

        return new ResponseEntity<>(result.get().getGrades(), HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public @ResponseBody User createUser(@RequestBody User u) {
        u.setId(LongGen.generateId());
        us.create(u);
        return u;
    }

    @PutMapping("/user/{id}/update")
    public @ResponseBody HttpStatus updateUser(@PathVariable long id, @RequestBody User u) {
        if (u.getId() == id) {
            us.update(u);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @DeleteMapping("/user/{id}/delete")
    public @ResponseBody HttpStatus deleteUser(@PathVariable long id) {
        Optional<User> u = us.findById(id);
        if (u.isPresent()) {
            us.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

}
