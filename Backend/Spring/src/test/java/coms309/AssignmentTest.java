package database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import coms309.database.dataobjects.*;

/**
 * Author: Nathan
 */
public class AssignmentTest {

	@Test
	public void testBasicSettersAndGetters() {
		Assignment a = new Assignment();
		
		Date now = Calendar.getInstance().getTime();
		
		a.setCreationDate(now);
		a.setDescription("Sample description");
		a.setDueDate(now);
		a.setExpectedOutput("Sample expected output");
		a.setId(Long.valueOf(1));
		a.setProblemStatement("Sample problem statement");
		a.setTemplate("Sample template");
		a.setTitle("Sample title");
		
		assertEquals(now, a.getCreationDate());
		assertEquals("Sample description", a.getDescription());
		assertEquals(now, a.getDueDate());
		assertEquals("Sample expected output", a.getExpectedOutput());
		assertEquals(1, a.getId());
		assertEquals("Sample problem statement", a.getProblemStatement());
		assertEquals("Sample template", a.getTemplate());
		assertEquals("Sample title", a.getTitle());
	}
	
	@Test
	public void testSetAndGetCourse() {
		Assignment a = new Assignment();
		
		Course c = new Course();
		
		a.setCourse(c);
		
		assertEquals(c, a.getCourse());
	}
	
	@Test
	public void testSetAndGetAssignmentFile() {
		Assignment a = new Assignment();
		
		AssignmentFile af = new AssignmentFile();
		
		a.setAssignmentFile(af);
		
		assertEquals(af, a.getAssignmentFile());
	}
	
}
