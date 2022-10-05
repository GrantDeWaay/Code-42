package coms309.controller;

import coms309.controller.generator.LongGen;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Course;
import coms309.database.dataobjects.Grade;
import coms309.database.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AssignmentController {

    @Autowired
    AssignmentService as;

    @GetMapping("/assignment/{id}")
    public @ResponseBody Assignment getAssignment(@PathVariable long id) {
        Optional<Assignment> a = as.findById(id);
        return a.orElse(null);
    }

    @GetMapping("/assignment/{id}/grades")
    public @ResponseBody Grade[] getAssignmentGradeList(@PathVariable long id) {
        // TODO
        return null;
    }

    @GetMapping("/assignment/{id}/course")
    public @ResponseBody Course getAssignmentCourse(@PathVariable long id) {
        // TODO
        return null;
    }

    @PostMapping("/assignment/create")
    public @ResponseBody Assignment createAssignment(@RequestBody Assignment a) {
        a.setId(LongGen.generateId());
        as.create(a);
        return a;
    }

    @PutMapping("/assignment/{id}/update")
    public @ResponseBody HttpStatus updateAssignment(@PathVariable long id, @RequestBody Assignment a) {
        if (a.getId() == id) {
            as.update(a);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/assignment/{id}/delete")
    public @ResponseBody HttpStatus deleteAssignment(@PathVariable long id) {
        Optional<Assignment> a = as.findById(id);
        if (a.isPresent()) {
            as.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
