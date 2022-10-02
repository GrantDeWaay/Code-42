package coms309.controller.login;

import coms309.database.dataobjects.User;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Random;

@RestController
public class LoginController {

    public static HashMap<String, Long> sessionTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    StringBuilder sb = new StringBuilder();

    Random r = new Random();

    private String generateSessionToken() {
        StringBuilder sb = new StringBuilder();
        while (sessionTokens.containsKey(sb.toString()) || sb.toString().equals("")) {
            for (int i = 0; i < 32; i++) {
                char c = (char) (33 + r.nextInt(93)); //33-126
                sb.append(c);
            }
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

    @GetMapping("/login/{username}/{password}")
    public @ResponseBody UserLogin userLogin(@PathVariable String username, @PathVariable String password) {
        UserLogin u = new UserLogin(); // Instead pull user from database
        if (u.getUsername().equals(username)) {
            String token = generateSessionToken();
            sessionTokens.put(token, u.getID());
            return u;
        } else {
            return null;
        }
    }

    @GetMapping("/login/token/{token}")
    public @ResponseBody User userLoginToken(@PathVariable String token) {
        User u = new User(); // Instead pull user from database
        return u;
    }

    @GetMapping("/")
    public HttpStatus serverTest() {
        return HttpStatus.OK;
    }

}
