package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	
	@GetMapping("/getTest")
	public String getTest(@RequestParam(value = "blah", defaultValue = "silly") String message) {
		return String.format("Hello, %s! You sent a GET request with a parameter!", message);
	}
	
	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "blah", defaultValue = "weird") String message) {
		//TODO
		return String.format("Hello, %s! You sent a POST request with a parameter!" +
				"\n Its the same thing as GET but with POST instead", message);
	}
	
	@PostMapping("/postTest2")
	public String postTest2(@RequestBody TestData testData) {
		//TODO
		return String.format("Hello, %s! You sent a post request with a request body!\n JSON is so cool!", testData.getMessage());
	}
	
	@DeleteMapping("/deleteTest")
	public void deleteTest() {
		// This doesn't return anything but is useful
	}
	
	@PutMapping("/putTest")
	public void putTest() {
		// Also doesn't return but can change things
		// Not all HTTP need to return stuff
	}
}
