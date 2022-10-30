package edu.iastate.code42.objects;

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

    public Assignment(JSONObject response){

    }

    @Override
    public String toString() {
        return this.title;
    }
}
