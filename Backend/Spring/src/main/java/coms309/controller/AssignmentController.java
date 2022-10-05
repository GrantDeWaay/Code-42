package coms309.controller;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static coms309.controller.login.LoginController.sessionTokens;

@RestController
public class AssignmentController {

    // Need to handle non valid tokens TODO

    // API related to assignment work page TODO

    @GetMapping("/assignment/{token}")
    public @ResponseBody Assignment[] getAssignmentList(@PathVariable String token) {
        long ID = sessionTokens.get(token);
        Assignment[] arr = null; // Get array of assignments that match user ID from database
        return arr;
    }

    /*
    Returns grades for user regardless of assignment
     */
    @GetMapping("/assignment/grade/{token}")
    public @ResponseBody Grade[] getGradeList(@PathVariable String token) {
        long ID = sessionTokens.get(token);
        Grade[] arr = null; // Get array of grades that match user ID from database
        return arr;
    }

    @PutMapping("assignment/create")
    public @ResponseBody HttpStatus createAssignment(@RequestBody Assignment a) {
        // Add assignment to database
        return HttpStatus.ACCEPTED;
    }

    /*
    Internal class for grades
    Maps one-to-one with database;
     */
    public static class Grade {
        double Grade;
        long Assignment_ID;
        long Student_ID;
        String Update; // Date of graded last updated

        public Grade(long Student_ID, long Assignment_ID, double Grade, String Update) {
            this.Student_ID = Student_ID;
            this.Assignment_ID = Assignment_ID;
            this.Grade = Grade;
            this.Update = Update;
        }

        public double getGrade() {
            return Grade;
        }

        public void setGrade(double grade) {
            Grade = grade;
        }

        public long getAssignment_ID() {
            return Assignment_ID;
        }

        public void setAssignment_ID(long assignment_ID) {
            Assignment_ID = assignment_ID;
        }

        public long getStudent_ID() {
            return Student_ID;
        }

        public void setStudent_ID(long student_ID) {
            Student_ID = student_ID;
        }

        public String getUpdate() {
            return Update;
        }

        public void setUpdate(String update) {
            Update = update;
        }
    }

}
