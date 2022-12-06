package edu.iastate.code42.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Course class
 * Represents a class with users, grades and assignments
 * @author Andrew
 */
public class Course {
    private int id;
    private String title;
    private String descrition;
    private String languages;

    /**
     * Creates new Course object with id, title, description and languages
     * @param id Unique identifier for object from SQL database as int
     * @param title Course title as String
     * @param descrition Course description as String
     * @param languages Course languages as comma separated list as String
     */
    public Course(int id, String title, String descrition, String languages){
        this.id = id;
        this.title = title;
        this.descrition = descrition;
        this.languages = languages;
    }

    /**
     * Creates new Course object from JSONObject from HTTP Request
     * @param response JSONObject containing course details
     * @throws JSONException
     */
    public Course(JSONObject response) throws JSONException {
        this.id = response.getInt("id");
        this.title = response.getString("title");
        this.descrition = response.getString("description");
        this.languages = response.getString("languages");
    }

    /**
     * Get value of id and return it
     * @return value of int <code>id</code>
     */
    public int getId() {
        return id;
    }

    /**
     * Change the id instance variable
     * @param id New course id as int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get value of id and return it
     * @return value of String <code>title</code>
     */
    public String getTitle() {
        return title;
    }

    /**
     * Change the title instance variable
     * @param title New course title as String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get value of id and return it
     * @return value of String <code>description</code>
     */
    public String getDescrition() {
        return descrition;
    }

    /**
     * Change the description instance variable
     * @param descrition New course description as String
     */
    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    /**
     * Get value of id and return it
     * @return value of String <code>languages</code>
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * Change the languages instance variable
     * @param languages New course languages as String
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
