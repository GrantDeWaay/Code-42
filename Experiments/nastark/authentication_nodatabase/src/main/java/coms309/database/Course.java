package coms309.database;

public class Course {

    private String name;

    private Integer catalogId;

    private String grade;

    public Course() {
        this.name = "";
        this.catalogId = -1;
        this.grade = "";
    }

    public Course(String name, Integer catalogId, String grade) {
        this.name = name;
        this.catalogId = catalogId;
        this.grade = grade;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
}
