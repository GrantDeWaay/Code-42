package coms309.controller.login;

import coms309.database.dataobjects.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Random;

@RestController
public class LoginController {

    public static HashMap<String, String> sessionTokens = new HashMap<String, String>();

    StringBuilder sb = new StringBuilder();

    Random r = new Random();

    private String generateSessionToken() {
        StringBuilder sb = new StringBuilder();
        while (sessionTokens.containsValue(sb.toString()) || sb.toString().equals("")) {
            for (int i = 0; i < 32; i++) {
                char c = (char) (33 + r.nextInt(93)); //33-126
                sb.append(c);
            }
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

    @GetMapping("/login/{username}{password}")
    public @ResponseBody UserLogin userLogin(String username, String password) {
        UserLogin u = new UserLogin(); // Instead pull user from database
        u.setUsername(username); // Above
        if (u.getUsername().equals(username)) {
            String token = generateSessionToken();
            sessionTokens.put(u.getUsername(), token);
            return u;
        } else {
            return null;
        }
    }

    @GetMapping("/login/token/{token}")
    public @ResponseBody User userLoginToken(String token) {
        User u = new User(); // Instead pull user from database
        return u;
    }

    @GetMapping("/")
    public HttpStatus serverTest() {
        return HttpStatus.OK;
    }
}
