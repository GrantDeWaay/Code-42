package coms309.controller;

import coms309.api.dataobjects.ApiAssignment;
import coms309.api.dataobjects.ApiCourse;
import coms309.api.dataobjects.ApiGrade;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Grade;
import coms309.database.services.AssignmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for assignment endpoints <p>
 * HTTP 200 = good request. <p>
 * HTTP 202 = good request and data has been changed. <p>
 * HTTP 403 = incorrect permission. <p>
 * HTTP 404 = user or data not found. <p>
 */
@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService as;

    /**
     * Get an assignment from its id.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/assignment/{id}".
     *
     * @param id    assignment's id
     * @param token permission token
     * @return HTTP response, assignment data
     */
    @ApiOperation(value = "Get an Assignment by ID", response = ApiAssignment.class, tags = "assignment-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/assignment/{id}")
    public @ResponseBody ResponseEntity<ApiAssignment> getAssignment(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Assignment> a = as.findById(id);

        if (!a.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiAssignment>(new ApiAssignment(a.get()), HttpStatus.OK);
    }

    /**
     * Get a list of grades from an assignment.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get request, path "/assignment/{id}/grades".
     *
     * @param id    assignment's id
     * @param token permission token
     * @return HTTP response, list of grades
     */
    @ApiOperation(value = "Get the Grades for an Assignment", response = Iterable.class, tags = "assignment-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/assignment/{id}/grades")
    public @ResponseBody ResponseEntity<Set<ApiGrade>> getAssignmentGradeList(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Assignment> result = as.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ApiGrade> grades = new HashSet<>();

        Iterator<Grade> iter = result.get().getGrades().iterator();

        while (iter.hasNext()) {
            grades.add(new ApiGrade(iter.next()));
        }

        return new ResponseEntity<Set<ApiGrade>>(grades, HttpStatus.OK);
    }

    /**
     * Get the course that an assignment belongs to.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Get Request, path "/assignment/{id}/course".
     *
     * @param id    assignment's id
     * @param token permission token
     * @return HTTP response, course data
     */
    @ApiOperation(value = "Get the Course an Assignment belongs to", response = ApiCourse.class, tags = "assignment-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/assignment/{id}/course")
    public @ResponseBody ResponseEntity<ApiCourse> getAssignmentCourse(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isLiveToken(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Assignment> result = as.findById(id);

        if (!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiCourse>(new ApiCourse(result.get().getCourse()), HttpStatus.OK);

    }

    /**
     * Create an assignment.
     * <p>
     * Client must be teacher or admin.
     * <p>
     * Post request, path "/assignment/create".
     *
     * @param a     assignment data
     * @param token permission token
     * @return HTTP response, assignment data with generated id
     */
    @ApiOperation(value = "Create a new Assignment and add it to the system", response = ApiAssignment.class, tags = "assignment-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "FORBIDDEN")
    })
    @PostMapping("/assignment/create")
    public @ResponseBody ResponseEntity<ApiAssignment> createAssignment(@RequestBody ApiAssignment a, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Assignment assignment = new Assignment(a);
        as.create(assignment);

        return new ResponseEntity<ApiAssignment>(new ApiAssignment(assignment), HttpStatus.OK);
    }

    /**
     * Update an assignment.
     * <p>
     * Client must be teacher or admin.
     * <p>
     * Put request, path "/assignment/{id}/update".
     *
     * @param id    assignment's id
     * @param a     assignment data
     * @param token permission token
     * @return HTTP response, assignment data conformation
     */
    @ApiOperation(value = "Update an existing assignment", response = HttpStatus.class, tags = "assignment-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
    })
    @PutMapping("/assignment/{id}/update")
    public @ResponseBody HttpStatus updateAssignment(@PathVariable long id, @RequestBody ApiAssignment a, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Assignment> optional = as.findById(id);

        if (!optional.isPresent()) return HttpStatus.NOT_FOUND;

        Assignment assignment = optional.get();

        if (!Objects.equals(a.getTitle(), "") && a.getTitle() != null) {
            assignment.setTitle(a.getTitle());
        }
        if (!Objects.equals(a.getDescription(), "") && a.getDescription() != null) {
            assignment.setDescription(a.getDescription());
        }
        if (!Objects.equals(a.getProblemStatement(), "") && a.getProblemStatement() != null) {
            assignment.setProblemStatement(a.getProblemStatement());
        }
        if (!Objects.equals(a.getTemplate(), "") && a.getTemplate() != null) {
            assignment.setTemplate(a.getTemplate());
        }
        if (!Objects.equals(a.getExpectedOutput(), "") && a.getExpectedOutput() != null) {
            assignment.setExpectedOutput(a.getExpectedOutput());
        }

        as.update(assignment);

        return HttpStatus.ACCEPTED;
    }

    /**
     * Delete an assignment.
     * <p>
     * Client must be student, teacher or admin.
     * <p>
     * Delete request, path "/assignment/{id}/delete".
     *
     * @param id    assignment's id
     * @param token permission token
     * @return HTTP response
     */
    @ApiOperation(value = "Delete an assignment from the system", response = HttpStatus.class, tags = "assignment-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "ACCEPTED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND"),
    })
    @DeleteMapping("/assignment/{id}/delete")
    public @ResponseBody HttpStatus deleteAssignment(@PathVariable long id, @RequestParam String token) {
        if (!UserTokens.isTeacher(token) && !UserTokens.isAdmin(token)) {
            return HttpStatus.FORBIDDEN;
        }
        Optional<Assignment> a = as.findById(id);
        if (a.isPresent()) {
            as.delete(id);
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

}
