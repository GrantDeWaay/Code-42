package coms309.controller;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static coms309.controller.login.LoginController.sessionTokens;

public class CourseController {

    // Need to handle non valid tokens TODO

    /*
    Gets courses that students are in
     */
    @GetMapping("/course/{token}")
    public @ResponseBody Course[] getCourseList(@PathVariable String token) {
        long ID = sessionTokens.get(token);
        Course[] arr = null; // Get array of courses that match user ID from database
        return arr;
    }

    @PutMapping("/course/create")
    public @ResponseBody HttpStatus createCourse(@RequestBody Course c) {
        // Add course to database
        return HttpStatus.ACCEPTED;
    }

    /*
    Could be in AssignmentController
    Used in Course Dashboard
     */
    @GetMapping("/course/assignment/{course}")
    public @ResponseBody Assignment[] getAssignmentListFromCourse(String course) {
        Assignment[] arr = null; // Search for elements with course ID in assignments database
        return arr;
    }

}
