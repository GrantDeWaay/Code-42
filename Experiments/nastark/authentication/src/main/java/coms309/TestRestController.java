package coms309;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import coms309.authentication.AuthenticationManager;
import coms309.authentication.AuthenticationToken;
import coms309.authentication.ReturnableToken;
import coms309.database.Course;
import coms309.database.CourseRepository;
import coms309.database.User;
import coms309.database.UserRepository;
import coms309.dataobjects.Authentication;

@RestController
class WelcomeController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private AuthenticationManager authManager = new AuthenticationManager();

    @PostMapping(value = "/auth",
                 consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ReturnableToken> auth(@RequestBody Authentication auth) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(auth.getPassword().getBytes(StandardCharsets.UTF_8));

            String hexString = DatatypeConverter.printHexBinary(hash);

            System.out.println(hexString);

            Optional<User> databaseResult = userRepository.findById(auth.getUsername());

            if(!databaseResult.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if(!hexString.equals(databaseResult.get().getPassword())) return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

            AuthenticationToken token = new AuthenticationToken(auth.getUsername(), 300000);

            authManager.addToken(token); // add new token with 5 minute lifespan

            return new ResponseEntity<ReturnableToken>(new ReturnableToken(token.getToken()), HttpStatus.OK);
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getcourses")
    public @ResponseBody ResponseEntity<Iterable<Course>> getAllCourses(@RequestParam String token) {
        if(!authManager.isTokenValid(token)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Iterable<Course> result = courseRepository.findAll();

        return new ResponseEntity<Iterable<Course>>(result, HttpStatus.OK);
    }

    @GetMapping("/getcourses/{course}")
    public @ResponseBody ResponseEntity<Course> getCourseById(@RequestParam String token, @PathVariable Integer course) {
        if(!authManager.isTokenValid(token)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Optional<Course> result = courseRepository.findById(course);

        if(!result.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Course>(result.get(), HttpStatus.OK);
    }

}
