package edu.iastate.code42.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Assignment {
    int id;
    private String assignmentName;
    private String statement;
    private String lang;
    private String description;

    private String teacherCode;
    private String studentCode;

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

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
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

    public String getTeacherCode() {
        return teacherCode;
    }

    public String getStudentCode() {
        return studentCode;
    }
}
