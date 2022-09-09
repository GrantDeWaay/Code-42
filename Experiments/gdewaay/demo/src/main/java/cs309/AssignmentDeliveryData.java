package cs309;

import java.util.Arrays;
import java.util.List;

public class AssignmentDeliveryData {
	
	private String message;
	static int number = 0;
	public static List<String> list = Arrays.asList("Make python print hello", "Add 1+1 in Python", "deez");
	public AssignmentDeliveryData() {
		
	}
	
	public AssignmentDeliveryData(String message, int id) {
		this.message = message;
		
	}

	public String getMessage() {
		return message;
	}
	
	public static String getLesson(int id) {
		return list.get(id);
	}
	public static void add(int num) {
		number += num;
	}
	public static void del() {
		number = 0;
	}
	public static int getAdd() {
		return number;
	}
}
