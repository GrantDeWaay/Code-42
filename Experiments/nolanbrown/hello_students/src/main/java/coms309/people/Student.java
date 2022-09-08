package coms309.people;

public class Student {

    private String name;
    private String classID;
    private String studentID;

    public Student(String name, String classID, String studentID) {
        this.name = name;
        this.classID = classID;
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public String getClassID() {
        return classID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // Silly example for experiments
    public boolean correctEnrollment() {
        return classID.length() > 0 && studentID.length() == 8;
    }
}
