package coms309.controller.login;

import coms309.api.dataobjects.ApiUserLogin;
import coms309.controller.generator.TokenGen;
import coms309.controller.token.UserTokens;
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
import java.util.Random;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
public class LoginController {

    @Autowired
    UserService us;
    private StringBuilder sb = new StringBuilder();
    private Random r = new Random();

    /**
     * Assigns user to unique session token.
     *
     * @param username user's username
     * @param password user's password
     * @return HTTP response, user data, and session token
     */
    @ApiOperation(value = "Login as a user with the given username and password", response = ApiUserLogin.class, tags = "login-controller")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),        
        @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/login/{username}/{password}")
    public @ResponseBody ResponseEntity<ApiUserLogin> userLogin(@PathVariable String username, @PathVariable String password) {
        Optional<User> u = us.findByUsername(username);
        if (u.isPresent()) {
            String token = TokenGen.generateSessionToken();
            if (u.get().getType().equals("student")) {
                UserTokens.studentTokens.put(token, u.get().getId());
            }
            if (u.get().getType().equals("teacher")) {
                UserTokens.teacherTokens.put(token, u.get().getId());
            }
            if (u.get().getType().equals("admin")) {
                UserTokens.adminTokens.put(token, u.get().getId());
            }
            ApiUserLogin ret = new ApiUserLogin(u.get());
            ret.setToken(token);
            return new ResponseEntity<ApiUserLogin>(ret, HttpStatus.OK);
        } else {
            return new ResponseEntity<ApiUserLogin>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deactivates user's session token
     *
     * @param username user's username
     * @param token    users token
     * @return HTTP response
     */
    @ApiOperation(value = "Log a User out", response = HttpStatus.class, tags = "login-controller")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),        
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/logout/{username}/{token}")
    public @ResponseBody HttpStatus userLogout(@PathVariable String username, @PathVariable String token) {
        Optional<User> u = us.findByUsername(username);
        if (!u.isPresent()) {
            return HttpStatus.NOT_FOUND;
        }
        if (UserTokens.removeToken(token)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Test if server is currently running
     *
     * @return HTTP response
     */
    @GetMapping("/")
    public HttpStatus serverTest() {
        return HttpStatus.OK;
    }

}
