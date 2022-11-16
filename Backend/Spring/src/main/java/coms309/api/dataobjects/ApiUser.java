package coms309.api.dataobjects;

import coms309.database.dataobjects.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ApiUser {

    /**
     * Unique integer ID for each user.
     */
    @ApiModelProperty(notes = "Unique ID for the User", name = "id", required = true, value = "1")
    private Long id;

    /**
     * Username associated with user.
     */
    @ApiModelProperty(notes = "Username for the User", name = "username", required = true, value = "jdoe")
    private String username;

    /**
     * First name of user.
     */
    @ApiModelProperty(notes = "First name of the User", name = "firstName", required = true, value = "John")
    private String firstName;

    /**
     * Last name of user.
     */
    @ApiModelProperty(notes = "Last name of the User", name = "lastName", required = true, value = "Doe")
    private String lastName;

    /**
     * Hash of user password used to authenticate.
     */
    @ApiModelProperty(notes = "Password for the User to login", name = "password", required = true, value = "password")
    private String password;

    /**
     * Email associated with user account.
     */
    @ApiModelProperty(notes = "Email for the User", name = "email", required = true, value = "jdoe@example.com")
    private String email;

    /**
     * Type of user.
     */
    @ApiModelProperty(notes = "Type of User (student, teacher, or admin)", name = "type", required = true, value = "student")
    private String type;

    /**
     * Time of user creation.
     */
    @ApiModelProperty(notes = "Creation date for User", name = "creationDate", required = true, value = "1970-01-01T00:00:00.00")
    private Date creationDate;

    /**
     * Default constructor.  All fields initialize to null.
     */
    public ApiUser() {

    }

    /**
     * Constructor.  Create an ApiUser from a database User.
     *
     * @param u database user to create from
     */
    public ApiUser(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.type = u.getType();
    }

    /**
     * Constructor.  Create an ApiUser with the fields initialized to the given values.
     *
     * @param id        id
     * @param username  username
     * @param firstName first name
     * @param lastName  last name
     * @param password  password
     * @param email     email for user
     * @param type      type of user
     */
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
     * Get the unique id for the user.
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique id for the user.
     *
     * @param id new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the username for the user.
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for the user.
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the first name for the user.
     *
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name for the user.
     *
     * @param firstName new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name for the user.
     *
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name for the user.
     *
     * @param lastName new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the password for the user.
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password for the user.
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the email for the user.
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email for the user.
     *
     * @param email new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the type of the user.
     *
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of user.
     *
     * @param type new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the creation date for the user.
     *
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date for the user.
     *
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
