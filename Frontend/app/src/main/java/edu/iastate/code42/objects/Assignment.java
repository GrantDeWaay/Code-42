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
    private int Points;
    private String UnitTests;
    private String studentCode;
    private static Assignment sAssignment;
    private final Context mAppContext;
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
        this.Lang = setUnitTests;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public String getStatement() {
        return Statement;
    }

    public String getLang() {
        return Lang;
    }

    public String getDescription() {
        return Description;
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
