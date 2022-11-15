package coms309.controller.token;

import coms309.api.dataobjects.ApiUser;
import coms309.database.dataobjects.User;
import coms309.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TokenController {

    @Autowired
    private UserService us;

    /**
     * Get user data from token
     *
     * @param token user's token
     * @return HTTP response, user data
     */
    @GetMapping("/token/user/{token}")
    public @ResponseBody ResponseEntity<ApiUser> getUserByToken(@PathVariable String token) {
        long id = UserTokens.getID(token);
        Optional<User> u = us.findById(id);
        if (u.isPresent()) {
            return new ResponseEntity<ApiUser>(new ApiUser(u.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
