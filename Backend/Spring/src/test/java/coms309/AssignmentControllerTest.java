package controller;

import com.google.gson.Gson;
import coms309.api.dataobjects.ApiAssignment;
import coms309.api.dataobjects.ApiAssignmentUnitTest;
import coms309.api.dataobjects.ApiUserLogin;
import coms309.controller.AssignmentController;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Author: Nathan
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AssignmentControllerTest {

    @LocalServerPort
    int port;

    String token;

    @Before
    public void init() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

        Response response = RestAssured.given().header("charset", "UTF-8").when().get("/login/testadmin/password");

        Gson g = new Gson();
        ApiUserLogin user = g.fromJson(response.getBody().asString(), ApiUserLogin.class);
        token = user.getToken();
    }

    @Test
    public void testAssignmentCreateUpdateDeleteHttp() {

        ApiAssignment cAssignment = new ApiAssignment();
        cAssignment.setTitle("System Testing Assignment");
        cAssignment.setDescription("Sample assignment used for testing C running");
        cAssignment.setProblemStatement("Sample problem statement");
        cAssignment.setTemplate("#include <stdio.h>\n\nint main(void) {\n\tprintf(\"Hello World\n\");\n\treturn 0;\n}\n");
        cAssignment.setExpectedOutput("");

        Gson g = new Gson();

        Response createResponse = RestAssured.given().header("Content-Type", "application/json").header("charset", "UTF-8").body(g.toJson(cAssignment)).queryParam("token", token).when().post("/assignment/create");

        assertEquals(200, createResponse.getStatusCode());

        ApiAssignment responseAssignment = g.fromJson(createResponse.getBody().asString(), ApiAssignment.class);
        assertEquals("System Testing Assignment", responseAssignment.getTitle());
        assertEquals("Sample assignment used for testing C running", responseAssignment.getDescription());
        assertEquals("Sample problem statement", responseAssignment.getProblemStatement());
        assertEquals("#include <stdio.h>\n\nint main(void) {\n\tprintf(\"Hello World\n\");\n\treturn 0;\n}\n", responseAssignment.getTemplate());

        cAssignment.setTitle("This is a new title!");
        cAssignment.setDescription("This is a new description!");
        cAssignment.setProblemStatement("This is a new problem statement!");
        cAssignment.setTemplate("This is a new template!");

        Response updateResponse = RestAssured.given().header("Content-Type", "application/json").header("charset", "UTF-8").body(g.toJson(cAssignment)).queryParam("token", token).pathParam("id", responseAssignment.getId()).when().put("assignment/{id}/update");

        assertEquals(200, updateResponse.getStatusCode());

        Response getResponse = RestAssured.given().header("charset", "UTF-8").queryParam("token", token).pathParam("id", responseAssignment.getId()).when().get("/assignment/{id}");

        assertEquals(200, getResponse.getStatusCode());

        responseAssignment = g.fromJson(getResponse.getBody().asString(), ApiAssignment.class);
        assertEquals("This is a new title!", responseAssignment.getTitle());
        assertEquals("This is a new description!", responseAssignment.getDescription());
        assertEquals("This is a new problem statement!", responseAssignment.getProblemStatement());
        assertEquals("This is a new template!", responseAssignment.getTemplate());

        Response deleteResponse = RestAssured.given().header("charset", "UTF-8").queryParam("token", token).pathParam("id", responseAssignment.getId()).when().delete("/assignment/{id}/delete");

        assertEquals(200, deleteResponse.getStatusCode());

    }

    @Test
    public void testUnitTests() throws JSONException {
        ApiAssignment cAssignment = new ApiAssignment();
        cAssignment.setTitle("System Testing Assignment");
        cAssignment.setDescription("Sample assignment used for testing C running");
        cAssignment.setProblemStatement("Sample problem statement");
        cAssignment.setTemplate("#include <stdio.h>\n\nint main(void) {\n\tprintf(\"Hello World\n\");\n\treturn 0;\n}\n");
        cAssignment.setExpectedOutput("");

        Gson g = new Gson();

        Response createResponse = RestAssured.given().header("Content-Type", "application/json").header("charset", "UTF-8").body(g.toJson(cAssignment)).queryParam("token", token).when().post("/assignment/create");

        assertEquals(200, createResponse.getStatusCode());

        ApiAssignment responseAssignment = g.fromJson(createResponse.getBody().asString(), ApiAssignment.class);
        assertEquals("System Testing Assignment", responseAssignment.getTitle());
        assertEquals("Sample assignment used for testing C running", responseAssignment.getDescription());
        assertEquals("Sample problem statement", responseAssignment.getProblemStatement());
        assertEquals("#include <stdio.h>\n\nint main(void) {\n\tprintf(\"Hello World\n\");\n\treturn 0;\n}\n", responseAssignment.getTemplate());

        ApiAssignmentUnitTest unitTest = new ApiAssignmentUnitTest();
        unitTest.setExpectedOutput("expected output");
        unitTest.setInput("input");

        Response unitTestCreateResponse = RestAssured.given().header("Content-Type", "application/json").header("charset", "UTF-8").body("[" + g.toJson(unitTest) + "]").queryParam("token", token).pathParam("id", responseAssignment.getId()).when().post("/assignment/{id}/unitTests");

        assertEquals(200, unitTestCreateResponse.getStatusCode());

        Response unitTestGetResponse = RestAssured.given().header("charset", "UTF-8").queryParam("token", token).pathParam("id", responseAssignment.getId()).when().get("/assignment/{id}/unitTests");

        System.out.println("\n\n\n\n" + unitTestGetResponse.body().asString() + "\n\n\n\n");

        JSONArray arr = new JSONArray(unitTestGetResponse.getBody().asString());

        assertEquals("expected output", arr.getJSONObject(0).getString("expectedOutput"));
        assertEquals("input", arr.getJSONObject(0).getString("input"));
    }

}
