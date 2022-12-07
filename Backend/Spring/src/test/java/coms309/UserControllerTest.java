package coms309;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import coms309.api.dataobjects.ApiUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.web.JsonPath;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createUser() {
        String body = "{\"username\": \"UnitTestA\",\"firstName\": \"Hello\",\"lastName\": \"World\"," +
                "\"password\": \"Hair\",\"email\":,\"bmn@umn.edu\",\"type\": \"student\"}";
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body(body).
                pathParams("token", "test").
                when().
                get("/capitalize");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        String json = response.getBody().asString();
        assertTrue(json.contains("UnitTestA"));
    }


}
