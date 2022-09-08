package coms309.people;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class StudentController {

    HashMap<String, Student> studentList = new HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input.
    // Springboot automatically converts the list to JSON format
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/student")
    public @ResponseBody HashMap<String, Student> getAllStudents() {
        // Note: Converts hashmap into JSON which is really nice
        return studentList;
    }

    // THIS IS THE CREATE OPERATION
    // Springboot automatically converts JSON input into a person object and
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/student")
    public @ResponseBody String createStudent(@RequestBody Student s) {
        // Using the ID as key because that is probably better
        studentList.put(s.getStudentID(), s);
        return "New person " + s.getName() + " added to database (not really)";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATH-VARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/student/{id}")
    public @ResponseBody Student getStudent(@PathVariable String studentID) {
        return studentList.get(studentID);
    }

    /**
     * Read operation created by me for experiments
     */
    @GetMapping("/student/check")
    public @ResponseBody String correctEnrollment(@RequestParam(value = "id") String studentID) {
        // Even though they are HTTP functions they can still be used as normal functions
        Student s = getStudent(studentID);
        if (s.correctEnrollment()) {
            return "The student is correctly enrolled";
        } else {
            deleteStudent(studentID);
            return "The student is incorrectly enrolled and was deleted";
        }

    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATH-VARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/student/{firstName}")
    public @ResponseBody Student updateStudent(@PathVariable String studentID, @RequestBody Student s) {
        studentList.replace(studentID, s);
        return studentList.get(studentID);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATH-VARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method

    @DeleteMapping("/student/{firstName}")
    public @ResponseBody HashMap<String, Student> deleteStudent(@PathVariable String studentID) {
        studentList.remove(studentID);
        return studentList;
    }
}