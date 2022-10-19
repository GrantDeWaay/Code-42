package coms309.controller.token;

import java.util.HashMap;

public class UserTokens {

    public static HashMap<String, Long> studentTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    public static HashMap<String, Long> teacherTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    public static HashMap<String, Long> adminTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    public static boolean isStudent(String token) {
        return studentTokens.containsKey(token);
    }

    public static boolean isTeacher(String token) {
        return teacherTokens.containsKey(token);
    }

    public static boolean isAdmin(String token) {
        return adminTokens.containsKey(token);
    }

    public static boolean isLiveToken(String token) {
        return isStudent(token) || isTeacher(token) || isAdmin(token);
    }

}
