package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import coms309.api.dataobjects.ApiAssignment;
import coms309.api.dataobjects.ApiUserLogin;

import com.google.gson.*;

@RunWith(SpringRunner.class)
public class CodeRunTest {
    
    // TODO figure out what's wrong with this, this will work for now
    int port = 8080;

    String token;

    @Before
    public void init() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

        Response response = RestAssured.given().
                                        header("charset", "UTF-8").
                                        when().
                                        get("/login/teststudent2/password");

        Gson g = new Gson();
        ApiUserLogin user = g.fromJson(response.getBody().asString(), ApiUserLogin.class);
        token = user.getToken();
    }

    @Test
    public void testAssignmentCreation() {

        ApiAssignment cAssignment = new ApiAssignment();
        cAssignment.setTitle("System Testing Assignment");
        cAssignment.setDescription("Sample assignment used for testing C running");
        cAssignment.setProblemStatement("Sample problem statement");
        cAssignment.setTemplate("#include <stdio.h>\n\nint main(void) {\n\tprintf(\"Hello World\n\");\n\treturn 0;\n}\n");
        cAssignment.setExpectedOutput("");

        Gson g = new Gson();

        Response response = RestAssured.given().
                                        header("Content-Type", "application/json").
                                        header("charset", "UTF-8").
                                        body(g.toJson(cAssignment)).
                                        queryParam("token", token).
                                        when().
                                        post("/assignment/create");

        assertEquals(202, response.getStatusCode());

        ApiAssignment responseAssignment = g.fromJson(response.getBody().asString(), ApiAssignment.class);
        assertEquals("System Testing Assignment", responseAssignment.getTitle());
        assertEquals("Sample assignment used for testing C running", responseAssignment.getDescription());
        assertEquals("Sample problem statement", responseAssignment.getProblemStatement());
        assertEquals("#include <stdio.h>\n\nint main(void) {\n\tprintf(\"Hello World\n\");\n\treturn 0;\n}\n", responseAssignment.getTemplate());

    }

}
