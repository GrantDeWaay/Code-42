package coms309.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import coms309.database.dataobjects.User;
import coms309.database.services.UserService;
import coms309.api.dataobjects.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@RestController
public class LoginController {

    public static HashMap<String, Long> sessionTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    StringBuilder sb = new StringBuilder();

    Random r = new Random();

    @Autowired
    UserService us;

    private String generateSessionToken() {
        StringBuilder sb = new StringBuilder();
        while (sessionTokens.containsKey(sb.toString()) || sb.toString().equals("")) {
            for (int i = 0; i < 16; i++) {
                char c = (char) (33 + r.nextInt(93)); //33-126
                sb.append(c);
            }
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

    @GetMapping("/login/{username}/{password}")
    public @ResponseBody
    ResponseEntity<ApiUser> userLogin(@PathVariable String username, @PathVariable String password) {
        Optional<User> u = us.findByUsername(username);
        if (u.isPresent()) {
            String token = generateSessionToken();
            sessionTokens.put(token, u.get().getId());
            return new ResponseEntity<ApiUser>(new ApiUser(u.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<ApiUser>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login/token/{token}")
    public @ResponseBody
    ResponseEntity<ApiUser> userLoginToken(@PathVariable String token) {
        long id = sessionTokens.get(token);
        Optional<User> u = us.findById(id);
        if (u.isPresent()) {
            return new ResponseEntity<ApiUser>(new ApiUser(u.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<ApiUser>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public HttpStatus serverTest() {
        return HttpStatus.OK;
    }

}
