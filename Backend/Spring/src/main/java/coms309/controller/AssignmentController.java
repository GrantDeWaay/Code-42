package coms309.controller;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Grade;

import coms309.api.dataobjects.*;

import coms309.database.services.AssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

@RestController
public class AssignmentController {

    @Autowired
    AssignmentService as;

    @GetMapping("/assignment/{id}")
    public @ResponseBody ResponseEntity<ApiAssignment> getAssignment(@PathVariable long id) {
        Optional<Assignment> a = as.findById(id);

        if(!a.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiAssignment(a.get()), HttpStatus.OK);
    }

    @GetMapping("/assignment/{id}/grades")
    public @ResponseBody ResponseEntity<Set<ApiGrade>> getAssignmentGradeList(@PathVariable long id) {
        Optional<Assignment> result = as.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiGrade> grades = new HashSet<>();

        Iterator<Grade> iter = result.get().getGrades().iterator();

        while(iter.hasNext()) {
            grades.add(new ApiGrade(iter.next()));
        }

        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/assignment/{id}/course")
    public @ResponseBody ResponseEntity<ApiCourse> getAssignmentCourse(@PathVariable long id) {
        Optional<Assignment> result = as.findById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiCourse(result.get().getCourse()), HttpStatus.OK);
    }

    @PostMapping("/assignment/create")
    public @ResponseBody ApiAssignment createAssignment(@RequestBody ApiAssignment a) {

        Assignment assignment = new Assignment(a);
        as.create(assignment);

        return new ApiAssignment(assignment);
    }

    @PutMapping("/assignment/{id}/update")
    public @ResponseBody HttpStatus updateAssignment(@PathVariable long id, @RequestBody ApiAssignment a) {
        Optional<Assignment> optional = as.findById(id);

        if(!optional.isPresent()) return HttpStatus.NOT_FOUND;

        Assignment assignment = optional.get();

        assignment.setTitle(a.getTitle());
        assignment.setDescription(a.getDescription());
        assignment.setProblemStatement(a.getProblemStatement());

        as.update(assignment);

        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/assignment/{id}/delete")
    public @ResponseBody HttpStatus deleteAssignment(@PathVariable long id) {
        Optional<Assignment> a = as.findById(id);
        if (a.isPresent()) {
            as.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

}
