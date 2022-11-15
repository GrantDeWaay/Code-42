package coms309.api.dataobjects;

import coms309.database.dataobjects.User;

public class ApiUserLogin extends ApiUser {

    /**
     * Session token to enforce API permissions.
     */
    private String token;

    /**
     * Constructor.
     * Create an ApiUserLogin from a database user.
     *
     * @param u
     */
    public ApiUserLogin(User u) {
        super(u);
    }

    /**
     * Constructor.  Sets the fields to the specified values.
     *
     * @param id        id
     * @param username  username
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param password  password
     * @param email     email of user
     * @param type      type of user (admin, teacher, student)
     * @param token     authentication token for user
     */
    public ApiUserLogin(Long id, String username, String firstName, String lastName, String password, String email, String type, String token) {
        super(id, username, firstName, lastName, password, email, type);
        this.token = token;
    }


    /**
     * Get the authentication token for the user.
     *
     * @return String
     */
    public String getToken() {
        return token;
    }


    /**
     * Set the authentication token for the user.
     *
     * @param token new authentication token
     */
    public void setToken(String token) {
        this.token = token;
    }

}
