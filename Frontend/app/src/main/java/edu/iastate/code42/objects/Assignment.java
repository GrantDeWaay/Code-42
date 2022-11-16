package edu.iastate.code42.objects;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Assignment class
 * Represents an assignment in a course
 * @author Andrew
 * @author Grant
 */
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

    /**
     *  Creates a new Assignment object with a context
     * @param c Context from activity or application
     */
    public Assignment(Context c){
        mAppContext = c.getApplicationContext();
    }

    /**
     * Creates a new Assignment object with the base information on Assignment Create screen
     * @param assignmentName String of assignment's name
     * @param statement String of assignment's problem statement
     * @param lang String of assignment's coding language
     * @param description String of assignment's problem description
     */
    public Assignment(String assignmentName, String statement, String lang, String description) {
        this.assignmentName = assignmentName;
        this.statement = statement;
        this.lang = lang;
        this.description = description;
    }

    /**
     * Creates new Assignment object from a JSONOject from HTTP request
     * @param response JSONObject containing assignment details
     * @throws JSONException
     */
    public Assignment(JSONObject response) throws JSONException {
        this.id = response.getInt("id");
        this.assignmentName = response.getString("title");
        this.statement = response.getString("problemStatement");
        this.description = response.getString("description");

    }

    /**
     * Method to get the Assignment object from the corresponding context
     * @param c Activity or application context
     * @return Assignment object
     */
    public static Assignment get(Context c){
        if(sAssignment == null){
            sAssignment = new Assignment(c);
        }
        return sAssignment;
    }

    /**
     * Change the Code instance variable
     * @param Code New code string
     */
    public void setCode(String Code) {
        this.Code = Code;
    }

    /**
     * Change the Points instance variable
     * @param Points New grade points as int
     */
    public void setPoints(int Points) {
        this.Points = Points;
    }

    /**
     * Change the assignmentName instance variable
     * @param AssignmentName New assignment name String
     */
    public void setAssignmentName(String AssignmentName) {
        this.AssignmentName = AssignmentName;
    }

    /**
     * Change the Statement instance variable
     * @param Statement New statement String
     */
    public void setStatement(String Statement) {
        this.Statement = Statement;
    }

    /**
     * Change the Lang instance variable
     * @param Lang New language String
     */
    public void setLang(String Lang) {
        this.Lang = Lang;
    }

    /**
     * Change the UnitTests instance variable
     * @param setUnitTests New unit tests String
     */
    public void setUnitTests(String setUnitTests) {
        this.UnitTests = setUnitTests;
    }

    /**
     * Change the Description instance variable
     * @param Description New description String
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * Get the value of id and return it
     * @return value of int <code>id</code>, unique identifier for the object from SQL database
     */
    public int getId() {
        return id;
    }

    /**
     * Change the id instance variable
     * @param id New id as int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of assignmentName and return it
     * @return value of String <code>assignmentName</code>
     */
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * Get the value of assignmentName and return it
     * @return value of String <code>assignmentName</code>
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Get the value of lang and return it
     * @return value of String <code>lang</code>
     */
    public String getLang() {
        return lang;
    }

    /**
     * Get the value of description and return it
     * @return value of String <code>description</code>
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the value of Code and return it
     * @return value of String <code>Code</code>
     */
    public String getCode() {
        return Code;
    }

    /**
     * Creates a JSONObject to be used in HTTP Requests
     * @return JSONObject with assignment details
     */
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
