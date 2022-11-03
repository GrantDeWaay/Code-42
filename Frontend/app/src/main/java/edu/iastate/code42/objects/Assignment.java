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

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
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

    public String getTeacherCode() {
        return teacherCode;
    }

    public String getStudentCode() {
        return studentCode;
    }
}
