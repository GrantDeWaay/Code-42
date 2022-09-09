package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import coms309.database.*;


@RestController
class WelcomeController {

    private TestRepository repository = new TestRepository();

    @GetMapping("/courses")
    public @ResponseBody ResponseEntity<Iterable<Course>> getAllCourses() {
        return new ResponseEntity<>(repository.findAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/courses/{id}")
    public @ResponseBody ResponseEntity<Course> getCourse(@PathVariable Integer id) {
        Optional<Course> result = repository.findCourseById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @GetMapping("/students")
    public @ResponseBody ResponseEntity<Iterable<Student>> getAllStudents() {
        return new ResponseEntity<>(repository.findAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public @ResponseBody ResponseEntity<Student> getStudent(@PathVariable Integer id) {
        Optional<Student> result = repository.findStudentById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @GetMapping("/students/{id}/courses")
    public @ResponseBody ResponseEntity<Iterable<Course>> getStudentCourses(@PathVariable Integer id) {
        Optional<Student> result = repository.findStudentById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LinkedList<Integer> courseIds = result.get().getEnrollment();

        LinkedList<Course> courses = new LinkedList<>();

        for(Integer i : courseIds) {
            Optional<Course> course = repository.findCourseById(i);

            if(course.isPresent()) courses.add(course.get());
        }

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @DeleteMapping("/students/{id}")
    public @ResponseBody ResponseEntity<Student> deleteStudent(@PathVariable Integer id) {
        Optional<Student> result = repository.findStudentById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        repository.removeStudent(id);

        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @DeleteMapping("/courses/{id}")
    public @ResponseBody ResponseEntity<Course> deleteCourse(@PathVariable Integer id) {
        Optional<Course> result = repository.findCourseById(id);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        repository.removeCourse(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/students")
    public @ResponseBody ResponseEntity<Student> addStudent(@RequestBody Student student) {
        if(!repository.addStudent(student)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/courses")
    public @ResponseBody ResponseEntity<Course> addStudent(@RequestBody Course course) {
        if(!repository.addCourse(course)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
