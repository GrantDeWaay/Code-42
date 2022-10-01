package coms309.api.dataobjects;

import coms309.database.dataobjects.User;

import java.util.Date;

public class ApiUser {

    // unique integer ID for each user
    private Long id;

    // username associated with user
    private String username;

    // first name of user
    private String firstName;

    // last name of user
    private String lastName;

    // hash of user password used to authenticate
    private String password;

    // email associated with user account
    private String email;

    // type of user (admin, teacher, etc)
    private String type;

    // time of user creation
    private Date creationDate;

    public ApiUser() {

    }

    public ApiUser(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.type = u.getType();
    }

    public ApiUser(Long id, String username, String firstName, String lastName, String password, String email, String type) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public void setId(Long id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
    }

    // returns the ID of the user
    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setType(String type) {
        // TODO add code to check for valid types
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

}
