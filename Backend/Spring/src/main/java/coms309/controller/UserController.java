package coms309.controller;

import com.sun.tools.javac.util.DefinedBy;
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

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

@RestController
public class UserController {


    @Autowired
    UserService us;

    @GetMapping("/user")
    public @ResponseBody ResponseEntity<List<ApiUser>> getUserList(@RequestParam String token) {
        if (UserTokens.isTeacher(token) || UserTokens.isAdmin(token)) {
            List<User> result = us.findAll();

            List<ApiUser> users = new LinkedList<>();

            for (User u : result) {
                users.add(new ApiUser(u));
            }

            return new ResponseEntity<List<ApiUser>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<ApiUser>>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/user/{id}")
    public @ResponseBody ResponseEntity<ApiUser> getUser(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<User> u = us.findById(id);

            if (!u.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(new ApiUser(u.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<ApiUser>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/user/{id}/courses")
    public @ResponseBody ResponseEntity<Set<ApiCourse>> getUserCourseList(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<User> result = us.findById(id);

            if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Set<ApiCourse> courses = new HashSet<>();

            Iterator<Course> iter = result.get().getCourses().iterator();

            while (iter.hasNext()) {
                courses.add(new ApiCourse(iter.next()));
            }

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } else {
            return new ResponseEntity<Set<ApiCourse>>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/user/{id}/grades")
    public @ResponseBody ResponseEntity<Set<ApiGrade>> getUserGradeList(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<User> result = us.findById(id);

            if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Set<ApiGrade> grades = new HashSet<>();

            Iterator<Grade> iter = result.get().getGrades().iterator();

            while (iter.hasNext()) {
                grades.add(new ApiGrade(iter.next()));
            }

            return new ResponseEntity<>(grades, HttpStatus.OK);
        } else {
            return new ResponseEntity<Set<ApiGrade>>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/user/create")
    public @ResponseBody ApiUser createUser(@RequestBody ApiUser u, @RequestParam String token) {
        if (UserTokens.isTeacher(token) || UserTokens.isAdmin(token)) {
            u.setCreationDate(Calendar.getInstance().getTime());

            User user = new User(u);
            us.create(user);

            return new ApiUser(user);
        } else {
            return new ResponseEntity<ApiUser>(HttpStatus.FORBIDDEN).getBody(); // This is needed to compile I guess
        }
    }

    @PutMapping("/user/{id}/update")
    public @ResponseBody HttpStatus updateUser(@PathVariable long id, @RequestBody ApiUser u, @RequestParam String token) {
        if (UserTokens.isLiveToken(token)) {
            Optional<User> optional = us.findById(id);

            if (!optional.isPresent()) return HttpStatus.NOT_FOUND;

            User user = optional.get();

            user.setUsername(u.getUsername());
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            user.setPassword(u.getPassword());
            user.setEmail(u.getEmail());
            user.setType(u.getType());

            us.update(user);

            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @DeleteMapping("/user/{id}/delete")
    public @ResponseBody HttpStatus deleteUser(@PathVariable long id, @RequestParam String token) {
        if (UserTokens.isTeacher(token) || UserTokens.isAdmin(token)) {
            Optional<User> u = us.findById(id);
            if (u.isPresent()) {
                us.delete(id);
                return HttpStatus.ACCEPTED;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

}
