package coms309.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {

    private String name;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
