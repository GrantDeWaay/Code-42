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

    public User(Context c){
        mAppContext = c.getApplicationContext();
    }

    public User(JSONObject response) throws JSONException {
        mAppContext = null;

        this.id = response.getLong("id");
        this.username = response.getString("username");
        this.firstName = response.getString("firstName");
        this.lastName = response.getString("lastName");
        this.email = response.getString("email");
        this.type = response.getString("type");
    }


    public static User get(Context c){
        if(sUser == null){
            sUser = new User(c);
        }
        return sUser;
    }

    public void fromJson(JSONObject response) throws JSONException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        this.id = response.getLong("id");
        this.username = response.getString("username");
        this.firstName = response.getString("firstName");
        this.lastName = response.getString("lastName");
        this.email = response.getString("email");
        this.type = response.getString("type");
    }

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

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && firstName.equals(user.firstName)
                && lastName.equals(user.lastName) && email.equals(user.email) && type.equals(user.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, type);
    }

    @Override
    public String toString() {
        return this.lastName + ", " + this.firstName + "\t" + this.username + "\t" + this.email;
    }
}
