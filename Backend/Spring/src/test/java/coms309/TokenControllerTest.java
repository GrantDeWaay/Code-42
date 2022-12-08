package coms309;

import coms309.api.dataobjects.ApiUser;
import coms309.api.dataobjects.ApiUserLogin;
import coms309.controller.UserController;
import coms309.controller.login.LoginController;
import coms309.controller.token.TokenController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Author: Nolan
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TokenControllerTest {

    @Autowired
    TokenController tc;

    @Autowired
    UserController uc;

    @Autowired
    LoginController lc;

    @Test
    public void checkTokenIdMap() {
        ApiUser u = new ApiUser(null, "nbrown3", null, null, "password123", "nbrown3@iastate.edu", "student");

        ResponseEntity<ApiUser> response1 = uc.createUser(u, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody().getId());

        ResponseEntity<ApiUserLogin> response2 = lc.userLogin("nbrown3", "password123");
        ResponseEntity<ApiUser> response3 = tc.getUserByToken(response2.getBody().getToken());
        assertEquals(u.getEmail(), response3.getBody().getEmail());

        uc.deleteUser(response1.getBody().getId(), "test");
        assertEquals(HttpStatus.NOT_FOUND, uc.getUserByEmail("nbrown3@iastate.edu").getStatusCode());
    }

}
