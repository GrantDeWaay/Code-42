package coms309;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Author: Nolan
 */
public class GradeTest {
    @Test
    public void testBasicSettersAndGetters() {

        Grade g = new Grade();
        Assignment a = new Assignment();
        User u = new User();
        Date d = new Date();

        g.setGrade(4.0);
        g.setAssignment(a);
        g.setId(64L);
        g.setUpdateDate(d);
        g.setUser(u);

        assertEquals(4.0, g.getGrade());
        assertEquals(a, g.getAssignment());
        assertEquals(64L, g.getId());
        assertEquals(d, g.getUpdateDate());
        assertEquals(u, g.getUser());

    }
}
