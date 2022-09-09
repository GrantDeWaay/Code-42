package coms309.authentication;

public class ReturnableToken {
 
    private String token;

    public ReturnableToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
