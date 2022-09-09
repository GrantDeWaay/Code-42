package coms309.database;

import java.util.LinkedList;

public class Course {

    private String name;

    private Integer catalogId;

    private LinkedList<Student> students;

    public Course() {
        this.name = "";
        this.catalogId = -1;
        this.students = new LinkedList<>();
    }

    public Course(String name, Integer catalogId) {
        this.name = name;
        this.catalogId = catalogId;
        this.students = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public LinkedList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.addCourse(this.catalogId);
    }
    
}
