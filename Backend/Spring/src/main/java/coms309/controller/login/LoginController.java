package coms309.controller.login;

import coms309.database.dataobjects.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import java.util.Random;


public class LoginController {

    public static HashMap<String, String> sessionTokens = new HashMap<String, String>();

    StringBuilder sb = new StringBuilder();

    Random r = new Random();

    private String generateSessionToken() {
        StringBuilder sb = new StringBuilder();
        while (sessionTokens.containsValue(sb.toString()) || sb.toString().equals("")){
            for (int i = 0; i < 32; i++) {
                char c = (char) (33 + r.nextInt(93)); //33-126
                sb.append(c);
            }
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

    @GetMapping("/login{username}{password}")
    public @ResponseBody String studentLogin(String username, String password) {
        User u = new User(); // Instead pull user from database TODO
        if (u.getUsername().equals(username)) {
            String token = sessionTokens.put(u.getUsername(), generateSessionToken());
            return "Login Success: " + token;
        } else {
            return "Username is not in database. Please sign up";
        }
    }
}
