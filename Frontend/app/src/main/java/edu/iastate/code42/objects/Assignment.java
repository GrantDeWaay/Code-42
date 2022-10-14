package edu.iastate.code42.objects;

public class Assignment {
    private String AssignmentName;
    private String Statement;
    private String Lang;
    private String Description;

    private String teacherCode;
    private String studentCode;

    public Assignment(String assignmentName, String statement, String lang, String description) {
        AssignmentName = assignmentName;
        Statement = statement;
        Lang = lang;
        Description = description;
    }
}
