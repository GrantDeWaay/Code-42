package coms309.controller.generator;

import coms309.controller.token.UserTokens;

import java.util.Random;

public class TokenGen {

    private static final Random r = new Random();

    public static String generateSessionToken() {
        StringBuilder sb = new StringBuilder();
        while (UserTokens.isLiveToken(String.valueOf(sb)) || sb.toString().equals("")) {
            for (int i = 0; i < 16; i++) {
                char c = (char) (33 + r.nextInt(93)); //33-126
                sb.append(c);
            }
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

}
