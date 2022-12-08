package coms309;

import com.google.gson.Gson;
import coms309.api.dataobjects.ApiUser;
import coms309.controller.UserController;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    UserController uc;

    @Before
    public void setUp() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void userCreateDelete() {
        ApiUser u = new ApiUser(null, null, "Joe", "Shoe", null, "UniqueEmail@gmail.com", "student");
        ResponseEntity<ApiUser> response = uc.createUser(u, "test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        uc.deleteUser(response.getBody().getId(), "test");
        assertEquals(HttpStatus.NOT_FOUND, uc.getUserByEmail("UniqueEmail@gmail.com").getStatusCode());
    }

    @Test
    public void userCreateUpdateDelete() {
        ApiUser u = new ApiUser(null, null, "Doe", "Brew", null, "NotUniqueEmail@gmail.com", "teacher");

        ResponseEntity<ApiUser> response1 = uc.createUser(u, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody().getId());

        u.setPassword("fish-tacos123");
        HttpStatus response2 = uc.updateUser(response1.getBody().getId(), u, "test");
        assertEquals(HttpStatus.ACCEPTED, response2);

        ResponseEntity<ApiUser> response3 = uc.getUserById(response1.getBody().getId(), "test");
        assertEquals("fish-tacos123", response3.getBody().getPassword());

        uc.deleteUser(response1.getBody().getId(), "test");
        assertEquals(HttpStatus.NOT_FOUND, uc.getUserByEmail("NotUniqueEmail@gmail.com").getStatusCode());
    }

    @Test
    public void userAddCourse(){
        ApiUser apiUser = new ApiUser();
        apiUser.setUsername("alksdbaljhsvd");

        Gson g = new Gson();

        Response response1 = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "UTF-8").
                body(g.toJson(apiUser)).
                queryParam("token", "test").
                when().
                post("/user/create");


    }

//        @Test
//    public void userCreateRestAssured() {
//        ApiUser apiUser = new ApiUser();
//        apiUser.setUsername("nbrown3");
//        apiUser.setEmail("nbrown3@iastate.edu");
//
//        Gson g = new Gson();
//
//        Response response1 = RestAssured.given().
//                header("Content-Type", "application/json").
//                header("charset", "UTF-8").
//                body(g.toJson(apiUser)).
//                queryParam("token", "test").
//                when().
//                post("/user/create");
//
//        assertEquals(200, response1.getStatusCode());
//
//        ApiUser returned = g.fromJson(response1.getBody().asString(), ApiUser.class);
//
//        assertEquals("nbrown3",returned.getUsername());
//        assertNotNull(returned.getId());
//
//    }

}
