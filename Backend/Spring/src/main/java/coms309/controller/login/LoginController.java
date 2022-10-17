package coms309.controller.login;

import coms309.controller.generator.TokenGen;
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

    @GetMapping("/login/{username}/{password}")
    public @ResponseBody
    ResponseEntity<ApiUserLogin> userLogin(@PathVariable String username, @PathVariable String password) {
        Optional<User> u = us.findByUsername(username);
        if (u.isPresent()) {
            String token = TokenGen.generateSessionToken();
            sessionTokens.put(token, u.get().getId());
            ApiUserLogin ret = new ApiUserLogin(u.get());
            ret.setToken(token);
            return new ResponseEntity<ApiUserLogin>(ret, HttpStatus.OK);
        } else {
            return new ResponseEntity<ApiUserLogin>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public HttpStatus serverTest() {
        return HttpStatus.OK;
    }

}
