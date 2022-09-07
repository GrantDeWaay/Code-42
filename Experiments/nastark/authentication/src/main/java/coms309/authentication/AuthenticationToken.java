package coms309.authentication;

import java.util.UUID;

public class AuthenticationToken {

    private String token;
    private String username;
    private long creationTime;
    private long timeToLive;

    public AuthenticationToken(String username, long timeToLive) {
        this.username = username;
        this.timeToLive = timeToLive;

        this.token = UUID.randomUUID().toString();
        this.creationTime = System.currentTimeMillis();
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getTimeToLive() {
        return creationTime;
    }

    public long getTimeRemaining() {
        return timeToLive - (System.currentTimeMillis() - creationTime);
    }

    public boolean isValid() {
        return getTimeRemaining() > 0;
    }

}