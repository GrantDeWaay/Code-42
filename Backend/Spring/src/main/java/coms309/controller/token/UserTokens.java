package coms309.controller.token;

import java.util.HashMap;

public class UserTokens {

    public static HashMap<String, Long> studentTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    public static HashMap<String, Long> teacherTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    public static HashMap<String, Long> adminTokens = new HashMap<String, Long>(); // (Key: Token, Value: ID)

    public static boolean isStudent(String token) {
        // Built in token for testing purpose
        // Should remove in final build
        if (token.equals("test")) {
            return true;
        }
        return studentTokens.containsKey(token);
    }

    public static boolean isTeacher(String token) {
        // Built in token for testing purpose
        // Should remove in final build
        if (token.equals("test")) {
            return true;
        }
        return teacherTokens.containsKey(token);
    }

    public static boolean isAdmin(String token) {
        // Built in token for testing purpose
        // Should remove in final build
        if (token.equals("test")) {
            return true;
        }
        return adminTokens.containsKey(token);
    }

    public static boolean isLiveToken(String token) {
        return isStudent(token) || isTeacher(token) || isAdmin(token);
    }

    public static boolean removeToken(String token) {
        if (studentTokens.containsKey(token)) {
            studentTokens.remove(token);
            return true;
        }
        if (teacherTokens.containsKey(token)) {
            teacherTokens.remove(token);
            return true;
        }
        if (adminTokens.containsKey(token)) {
            adminTokens.remove(token);
            return true;
        }
        return false;
    }

    public static long getID(String token) {
        if (studentTokens.containsKey(token)) {
            return studentTokens.get(token);
        }
        if (teacherTokens.containsKey(token)) {
            return teacherTokens.get(token);
        }
        if (adminTokens.containsKey(token)) {
            return adminTokens.get(token);
        }
        return -1;
    }

}
