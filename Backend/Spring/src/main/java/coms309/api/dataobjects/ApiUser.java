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

    
    /** 
     * @param id
     */
    public void setId(Long id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
    }

    
    /** 
     * @return Long
     */
    // returns the ID of the user
    public Long getId() {
        return id;
    }

    
    /** 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    
    /** 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /** 
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    
    /** 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    /** 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    
    /** 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @param type
     */
    public void setType(String type) {
        // TODO add code to check for valid types
        this.type = type;
    }

    
    /** 
     * @return String
     */
    public String getType() {
        return type;
    }

    
    /** 
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    /** 
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

}
