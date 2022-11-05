package edu.iastate.code42.objects;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class Assignment {
    private String AssignmentName;
    private String Statement;
    private String Lang;
    private String Description;
    private String Code;
    private int id;
    private int Points;
    private String statement;
    private String lang;
    private String description;
    private String UnitTests;
    private String studentCode;
    private static Assignment sAssignment;
    private Context mAppContext;
    private String assignmentName;

    public Assignment(Context c){
        mAppContext = c.getApplicationContext();
    }
    public static Assignment get(Context c){
        if(sAssignment == null){
            sAssignment = new Assignment(c);
        }
        return sAssignment;
    }


    public void setCode(String Code) {
        this.Code = Code;
    }

    public void setPoints(int Points) { this.Points = Points;}

    public void setAssignmentName(String AssignmentName) {
        this.AssignmentName = AssignmentName;
    }

    public void setStatement(String Statement) {
        this.Statement = Statement;
    }

    public void setLang(String Lang) {
        this.Lang = Lang;
    }
    public void setUnitTests(String setUnitTests) {
        this.UnitTests = setUnitTests;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }
    public Assignment(String assignmentName, String statement, String lang, String description) {
        this.assignmentName = assignmentName;
        this.statement = statement;
        this.lang = lang;
        this.description = description;
    }

    public Assignment(JSONObject response) throws JSONException {
        this.id = response.getInt("id");
        this.assignmentName = response.getString("title");
        this.statement = response.getString("problemStatement");
        this.description = response.getString("description");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getStatement() {
        return statement;
    }

    public String getLang() {
        return lang;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return Code;
    }

    public JSONObject formatJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", this.AssignmentName);
            obj.put("description", this.Description);
            obj.put("problemStatement", this.Statement);
            obj.put("lang", this.Lang);
            obj.put("template", this.Code);
            obj.put("expectedOutput", this.UnitTests);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
