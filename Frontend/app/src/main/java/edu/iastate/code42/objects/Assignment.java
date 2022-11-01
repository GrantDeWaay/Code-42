package edu.iastate.code42.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Assignment {
    private int id;

    private String title;
    private String description;
    private String problemStatement;

    public Assignment(int id, String title, String description, String problemStatement){
        this.id = id;
        this.title = title;
        this.description = description;
        this.problemStatement = problemStatement;
    }

    public Assignment(JSONObject response) throws JSONException {
        this.id = response.getInt("id");
        this.title = response.getString("title");
        this.description = response.getString("description");
        this.problemStatement = response.getString("problemStatement");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
