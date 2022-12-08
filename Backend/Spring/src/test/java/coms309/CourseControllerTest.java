package coms309;

import coms309.api.dataobjects.ApiCourse;
import coms309.api.dataobjects.ApiUser;
import coms309.controller.CourseController;
import coms309.controller.UserController;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Nolan
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CourseControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    CourseController cc;

    @Autowired
    UserController uc;

    @Test
    public void createCourse() {
        ApiCourse apiCourse = new ApiCourse();
        apiCourse.setTitle("How to Fight");
        apiCourse.setLanguages("English");

        ResponseEntity<ApiCourse> response1 = cc.createCourse(apiCourse, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(apiCourse.getTitle(), response1.getBody().getTitle());
        assertNotNull(response1.getBody().getId());

        HttpStatus response2 = cc.deleteCourse(response1.getBody().getId(), "test");
    }

    @Test
    public void updateCourse() {
        ApiCourse apiCourse = new ApiCourse();
        apiCourse.setTitle("How to Fight");
        apiCourse.setLanguages("English");

        ResponseEntity<ApiCourse> response1 = cc.createCourse(apiCourse, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(apiCourse.getTitle(), response1.getBody().getTitle());
        assertNotNull(response1.getBody().getId());

        apiCourse.setTitle("Do not Fight");

        HttpStatus response2 = cc.updateCourse(response1.getBody().getId(), apiCourse, "test");
        assertEquals(HttpStatus.OK, response1.getStatusCode());

        ResponseEntity<ApiCourse> response3 = cc.getCourse(response1.getBody().getId(), "test");

        assertEquals(apiCourse.getTitle(), response3.getBody().getTitle());

        HttpStatus response4 = cc.deleteCourse(response1.getBody().getId(), "test");
        assertEquals(HttpStatus.ACCEPTED, response4);
    }

    @Test
    public void getCourseList() {
        ApiCourse apiCourse = new ApiCourse();
        apiCourse.setTitle("Intro to Cooking");
        apiCourse.setLanguages("Spanish");

        ResponseEntity<ApiCourse> response1 = cc.createCourse(apiCourse, "test");
        int size1 = cc.getCourseList("test").getBody().size();
        assertNotEquals(0, size1);

        cc.deleteCourse(response1.getBody().getId(), "test");
        int size2 = cc.getCourseList("test").getBody().size();
        assertTrue(size1 != size2);

    }

}
