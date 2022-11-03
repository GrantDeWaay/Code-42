package edu.iastate.code42.utils;

public class Const {
	public static final String SOURCE = "http://coms-309-028.class.las.iastate.edu:8080/";
	//public static final String SOURCE = "https://a6629e8b-03c4-4b87-882c-d92549b52f9e.mock.pstmn.io/";

	public static final String TOKEN = "?token=%s";

	public static final String LOGIN = SOURCE + "login/%s/%s";
	public static final String LOGOUT = SOURCE + "logout/%s/%s";
	public static final String SESSION = SOURCE + "token/user/%s";

	public static final String GET_USERS = SOURCE + "user" + TOKEN;
	public static final String CREATE_USER = SOURCE + "user/create" + TOKEN;
	public static final String GET_USER = SOURCE + "user/%d" + TOKEN;
	public static final String GET_COURSES_FOR_USER = SOURCE + "user/%d/courses" + TOKEN;

	public static final String GET_COURSES = SOURCE + "course" + TOKEN;
	public static final String CREATE_COURSE = SOURCE + "course/create" + TOKEN;
	public static final String GET_COURSE = SOURCE + "course/%d" + TOKEN;
	public static final String GET_ASSIGNMENTS_FOR_COURSE = SOURCE + "course/%d/assignments" + TOKEN;
	public static final String GET_STUDENTS_FOR_COURSE = SOURCE + "course/%d/students" + TOKEN;
	public static final String GET_TEACHERS_FOR_COURSE = SOURCE + "course/%d/teachers" + TOKEN;
	public static final String ADD_USER_TO_COURSE = SOURCE + "course/%d/user/%d" + TOKEN;

	public static final String GET_ASSIGNMENT = SOURCE + "assignment/%d" + TOKEN;
	public static final String CREATE_ASSIGNMENT = SOURCE + "assignment/create" + TOKEN;

	public static final String PYTHON = "http://192.168.56.1:5000/";

}
