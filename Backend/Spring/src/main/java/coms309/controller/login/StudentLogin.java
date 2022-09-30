package coms309.controller.login;

/*
    Unused right now because login no longer uses JSON...
    Keeping just in case we switch back
 */
public class StudentLogin {

    private String username;

    private String hashedPassword;

    public StudentLogin(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
