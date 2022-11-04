package edu.iastate.code42.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Course {

    private int id;
    private String title;
    private String descrition;
    private String languages;

    public Course(int id, String title, String descrition, String languages){
        this.id = id;
        this.title = title;
        this.descrition = descrition;
        this.languages = languages;
    }

    public Course(JSONObject response) throws JSONException {
        this.id = response.getInt("id");
        this.title = response.getString("title");
        this.descrition = response.getString("description");
        this.languages = response.getString("languages");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
