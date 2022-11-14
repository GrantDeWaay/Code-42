package coms309.controller.generator;

import coms309.controller.token.UserTokens;

import java.util.Random;

public class TokenGen {

    private static final Random r = new Random();

    /**
     * Generates unique session token to be assigned to user.
     *
     * @return unique session token
     */
    public static String generateSessionToken() {
        StringBuilder sb = new StringBuilder();
        while (UserTokens.isLiveToken(String.valueOf(sb)) || sb.toString().equals("")) {
            for (int i = 0; i < 16; i++) {
                char c = (char) (65 + r.nextInt(26)); //65-90
                sb.append(c);
            }
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

}
