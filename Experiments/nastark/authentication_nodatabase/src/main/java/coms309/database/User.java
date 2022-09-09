package coms309.database;

public class User {
    
    private String username;

    private String password;

    private Integer userId;

    public User() {
        this.username = "";
        this.password = "";
        this.userId = -1;
    }

    public User(String username, String password, Integer userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
