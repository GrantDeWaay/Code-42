package coms309;

import com.google.gson.Gson;
import coms309.api.dataobjects.ApiUser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RestAssuredTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createUserAgain() {

        Response r1 = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "UTF-8").
                when().
                queryParam("token", "test").
                get("/");


        Response r2 = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "UTF-8").
                when().
                queryParam("token", "test").
                get("/user");

    }


}
