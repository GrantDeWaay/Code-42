package cs309;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
public class AssignmentDeliveryController {
	
	@GetMapping("/HelloWorld")
	public String HelloWorld(@RequestParam(value="username", defaultValue="World") String message)
	{
		return String.format("Hello, %s! You sent a get request with a parameter!", message);
	}
	
	@PostMapping("/getAssignment/{assignmentID}")
	public String getTest(@RequestBody AssignmentDeliveryData bodymessage, @PathVariable("assignmentID") int id) {
		String lessonPlan = AssignmentDeliveryData.getLesson(id);
		String username = bodymessage.getMessage();
		return String.format("Hello, %s! This is your lesson for today:\n%s", username, lessonPlan);
		//http://localhost:8080/getTest?username=x
	}
	@GetMapping("/add/get")
	public int getNumber() {
		int x = AssignmentDeliveryData.getAdd();
		//http://localhost:8080/getTest?username=x
		return x;
	}
	@DeleteMapping("/add/reset")
	public ResponseEntity resetNumber() {
		AssignmentDeliveryData.del();
		//http://localhost:8080/getTest?username=x
		return ResponseEntity.ok().build(); 
	}
	@PutMapping("/add/{number}")
	public ResponseEntity addNumber(@PathVariable("number") int id) {
		AssignmentDeliveryData.add(id);
		//http://localhost:8080/getTest?username=x
		return ResponseEntity.ok().build(); 
	}
	
}
