package coms309;

import coms309.api.dataobjects.ApiUser;
import coms309.api.dataobjects.ApiUserLogin;
import coms309.controller.UserController;
import coms309.controller.login.LoginController;
import coms309.controller.token.UserTokens;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.annotation.Persistent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class LoginControllerTest {

    @Autowired
    LoginController lc;

    @Autowired
    UserController uc;

    @Test
    public void userLoginLogoutStudent() {
        ApiUser u = new ApiUser(null, "nbrown3", "Nolan", "Brown", "password123", "nbrown3@iastate.edu", "student");

        ResponseEntity<ApiUser> response1 = uc.createUser(u, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody().getId());

        ResponseEntity<ApiUserLogin> response2 = lc.userLogin("nbrown3", "password123");
        assertTrue(UserTokens.isLiveToken(response2.getBody().getToken()));
        assertNotNull(response2.getBody().getId());

        assertEquals(response2.getBody().getId(), UserTokens.getID(response2.getBody().getToken()));

        HttpStatus response3 = lc.userLogout("nbrown3", response2.getBody().getToken());
        assertEquals(HttpStatus.OK, response3);

        uc.deleteUser(response1.getBody().getId(), "test");
        assertEquals(HttpStatus.NOT_FOUND, uc.getUserByEmail("nbrown3@iastate.edu").getStatusCode());
    }

    @Test
    public void userLoginLogoutTeacher() {
        ApiUser u = new ApiUser(null, "gbrown1", "Gerald", "Brown", "password456", "gbrown1@iastate.edu", "teacher");

        ResponseEntity<ApiUser> response1 = uc.createUser(u, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody().getId());

        ResponseEntity<ApiUserLogin> response2 = lc.userLogin("gbrown1", "password456");
        assertTrue(UserTokens.isLiveToken(response2.getBody().getToken()));
        assertNotNull(response2.getBody().getId());

        assertEquals(response2.getBody().getId(), UserTokens.getID(response2.getBody().getToken()));

        HttpStatus response3 = lc.userLogout("gbrown1", response2.getBody().getToken());
        assertEquals(HttpStatus.OK, response3);

        uc.deleteUser(response1.getBody().getId(), "test");
        assertEquals(HttpStatus.NOT_FOUND, uc.getUserByEmail("gbrown1@iastate.edu").getStatusCode());
    }

    @Test
    public void userLoginLogoutAdmin() {
        ApiUser u = new ApiUser(null, "rbrown2", "Riley", "Brown", "password789", "rbrown2@iastate.edu", "admin");

        ResponseEntity<ApiUser> response1 = uc.createUser(u, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody().getId());

        ResponseEntity<ApiUserLogin> response2 = lc.userLogin("rbrown2", "password789");
        assertTrue(UserTokens.isLiveToken(response2.getBody().getToken()));
        assertNotNull(response2.getBody().getId());

        assertEquals(response2.getBody().getId(), UserTokens.getID(response2.getBody().getToken()));

        HttpStatus response3 = lc.userLogout("rbrown2", response2.getBody().getToken());
        assertEquals(HttpStatus.OK, response3);

        uc.deleteUser(response1.getBody().getId(), "test");
        assertEquals(HttpStatus.NOT_FOUND, uc.getUserByEmail("rbrown2@iastate.edu").getStatusCode());
    }


    @Test
    public void severPing() {
        HttpStatus response1 = lc.serverTest();
        assertEquals(HttpStatus.OK, response1);
    }


}
