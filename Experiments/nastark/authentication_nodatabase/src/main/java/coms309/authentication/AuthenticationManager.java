package coms309.authentication;

import java.util.HashMap;

public class AuthenticationManager {

    private HashMap<String, AuthenticationToken> tokens;

    public AuthenticationManager() {
        tokens = new HashMap<>();
    }

    public boolean isTokenValid(String token) {
        AuthenticationToken authToken = tokens.get(token);

        if(token == null) return false;

        if(!authToken.isValid()) {
            tokens.remove(token);
            return false;
        }

        return true;
    }

    public boolean addToken(AuthenticationToken token) {
        if(tokens.containsKey(token.getToken())) return false;

        tokens.put(token.getToken(), token);
        return true;
    }

}