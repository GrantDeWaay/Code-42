package edu.iastate.code42.objects;

import android.app.Application;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * User class
 * Represents a user
 * @author Andrew
 */
public class User extends Application {
    private Long id;

    // username associated with user
    private String username;

    // first name of user
    private String firstName;

    // last name of user
    private String lastName;

    // email associated with user account
    private String email;

    // type of user (admin, teacher, etc)
    private String type;

    // time of user creation
    private Date creationDate;

    private static User sUser;

    private final Context mAppContext;

    /**
     * Create new User object with context
     * @param c Activity or application context
     */
    public User(Context c){
        mAppContext = c.getApplicationContext();
    }

    /**
     * Creates new User object from JSONObject from HTTP Request with no context
     * @param response JSONObject containing course details
     * @throws JSONException
     */
    public User(JSONObject response) throws JSONException {
        mAppContext = null;

        this.id = response.getLong("id");
        this.username = response.getString("username");
        this.firstName = response.getString("firstName");
        this.lastName = response.getString("lastName");
        this.email = response.getString("email");
        this.type = response.getString("type");
    }

    /**
     * Method to get the User object from the corresponding context
     * @param c Activity or application context
     * @return User object
     */
    public static User get(Context c){
        if(sUser == null){
            sUser = new User(c);
        }
        return sUser;
    }

    /**
     * Sets variables of existing User object from JSONObject from HTTP Request
     * @param response JSONObject containing course details
     * @throws JSONException
     */
    public void fromJson(JSONObject response) throws JSONException{
        this.id = response.getLong("id");
        this.username = response.getString("username");
        this.firstName = response.getString("firstName");
        this.lastName = response.getString("lastName");
        this.email = response.getString("email");
        this.type = response.getString("type");
    }

    /**
     * Delete User object on logout HTTP Request
     */
    public void logout(){
        sUser = null;
        this.id = null;
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.type = null;
        this.creationDate = null;
    }

    /**
     * Get full name of the User
     * @return String combination of value of <code>firstName</code> and value of <code>lastName</code>
     */
    public String getFullName(){
        return firstName + " " + lastName;
    }

    /**
     * Get value of id and return it
     * @return value of Long <code>id</code>
     */
    public Long getId() {
        return id;
    }

    /**
     * Change the id instance variable
     * @param id New user id as long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get value of username and return it
     * @return value of String <code>username</code>
     */
    public String getUsername() {
        return username;
    }

    /**
     * Change the username instance variable
     * @param username New user username as String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get value of firstName and return it
     * @return value of String <code>firstName</code>
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Change the firstName instance variable
     * @param firstName New user first name as String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get value of lastName and return it
     * @return value of String <code>lastName</code>
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Change the id instance variable
     * @param lastName New user last name as String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get value of email and return it
     * @return value of String <code>email</code>
     */
    public String getEmail() {
        return email;
    }

    /**
     * Change the email instance variable
     * @param email New user email as String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get value of type and return it
     * @return value of String <code>type</code>
     */
    public String getType() {
        return type;
    }

    /**
     * Change the type instance variable
     * @param type New user type as String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Override equals to compare User objects based on id, username, and user type,
     * used by Comparator and contains()
     * @param o Object to compare with
     * @return boolean of whether Object <code>o</code> equals User <code>this</code>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && type.equals(user.type);
    }

    /**
     * Override method for companion with equals()
     * @return Hash int of User object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, type);
    }

    /**
     * Creates and returns a string representation of User
     * @return String of lastname, firstname, username and email
     */
    @Override
    public String toString() {
        return this.lastName + ", " + this.firstName + "\t" + this.username + "\t" + this.email;
    }
}
