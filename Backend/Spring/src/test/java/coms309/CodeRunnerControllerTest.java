package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import coms309.api.dataobjects.ApiAssignment;
import coms309.api.dataobjects.ApiAssignmentUnitTest;
import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.api.dataobjects.ApiUserLogin;

import com.google.gson.*;

/**
 * Author: Nathan
 */
@RunWith(SpringRunner.class)
public class CodeRunnerControllerTest {
    
    // TODO figure out what's wrong with this, this will work for now
    int port = 8080;

    String token;

    @Before
    public void init() {
        RestAssured.port = port;
        // RestAssured.baseURI = "http://localhost";
        RestAssured.baseURI = "http://coms-309-028.class.las.iastate.edu";

        Response response = RestAssured.given().
                                        header("charset", "UTF-8").
                                        when().
                                        get("/login/teststudent3/password");

        Gson g = new Gson();
        ApiUserLogin user = g.fromJson(response.getBody().asString(), ApiUserLogin.class);
        token = user.getToken();
    }

    @Test
    public void testAssignmentSubmission() throws JSONException {

        ApiCodeSubmission submission = new ApiCodeSubmission();
        submission.setName("testassignment.c");
        submission.setLanguage("C");
        submission.setContents("#include <stdio.h>\nint main(void) {\nint nums[] = {0, 0, 0, 0, 0};\nscanf(\"%d %d %d %d %d\", &nums[0], &nums[1], &nums[2], &nums[3], &nums[4]);\nint min = nums[0];\nfor(int i = 1; i < 5; i++) {\nif(nums[i] < min) min = nums[i];\n}\nprintf(\"%d\\n\", min);\nreturn 0;\n}");

        Gson g = new Gson();
        
        Response response = RestAssured.given().
                                        header("Content-Type", "application/json").
                                        header("charset", "UTF-8").
                                        body(g.toJson(submission)).
                                        queryParam("token", token).
                                        pathParam("id", 117).
                                        when().
                                        put("/run/{id}");

        assertEquals(202, response.getStatusCode());

        JSONArray arr = new JSONArray(response.getBody().asString());

        for(int i = 0; i < arr.length(); i++) {
            assertEquals(true, arr.getJSONObject(i).getBoolean("passed"));
        }

    }
    
    @Test
    public void testAssignmentSubmissionFailure() throws JSONException {

        ApiCodeSubmission submission = new ApiCodeSubmission();
        submission.setName("testassignment.c");
        submission.setLanguage("C");
        submission.setContents("#include <stdio.h>\nint main(void) {\nint nums[] = {0, 0, 0, 0, 0};\nscanf(\"%d %d %d %d %d\", &nums[0], &nums[1], &nums[2], &nums[3], &nums[4]);\nint min = nums[0];\nfor(int i = 1; i < 5; i++) {\nif(nums[i] < min) min = nums[i];\n}\nprintf(\"%d\\n\", 2);\nreturn 0;\n}");

        Gson g = new Gson();
        
        Response response = RestAssured.given().
                                        header("Content-Type", "application/json").
                                        header("charset", "UTF-8").
                                        body(g.toJson(submission)).
                                        queryParam("token", token).
                                        pathParam("id", 117).
                                        when().
                                        put("/run/{id}");

        assertEquals(202, response.getStatusCode());

        JSONArray arr = new JSONArray(response.getBody().asString());

        boolean allPassed = true;

        for(int i = 0; i < arr.length(); i++) {
            if(!arr.getJSONObject(i).getBoolean("passed")) allPassed = false;
        }

        assertEquals(false, allPassed);

    }


}
