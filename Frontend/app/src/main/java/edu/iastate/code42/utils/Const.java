package edu.iastate.code42.utils;

public class Const {
	//public static final String SOURCE = "http://coms-309-028.class.las.iastate.edu:8080/";
	public static final String SOURCE = "https://a6629e8b-03c4-4b87-882c-d92549b52f9e.mock.pstmn.io/";

	public static final String LOGIN = SOURCE + "login/%s/%s";
	public static final String SESSION = SOURCE + "login/token/%d";

	public static final String GET_USER = SOURCE + "/user/%d";
	public static final String GET_COURSES_FOR_USER = SOURCE + "/user/%d/courses";

	public static final String CREATE_COURSE = SOURCE + "course/create";
	public static final String GET_COURSE = SOURCE + "course/%d";
	public static final String GET_ASSIGNMENTS_FOR_COURSE = SOURCE + "/course/%d/assignments";
	public static final String GET_STUDENTS_FOR_COURSE = SOURCE + "/course/%d/students";

	public static final String GET_ASSIGNMENT = SOURCE + "assignment/%d";
	public static final String CREATE_ASSIGNMENT = SOURCE + "assignment/create";

	public static final String PYTHON = "http://192.168.56.1:5000/";

}
