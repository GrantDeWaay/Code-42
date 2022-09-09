package coms309.database;

import java.util.LinkedList;

public class Student {
    
    private Integer id;

    private String name;

    private Integer grade;

    private LinkedList<Integer> enrollment;

    public Student() {
        this.id = -1;
        this.name = "";
        this.grade = -1;
        enrollment = new LinkedList<>();
    }

    public Student(Integer id, String name, Integer grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        enrollment = new LinkedList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LinkedList<Integer> getEnrollment() {
        return enrollment;
    }

    public void addCourse(Integer courseId) {
        enrollment.add(courseId);
    }

}
