package coms309.database.dataobjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationDescription.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    // TODO add way to track lists of students in course

    public Course() {

    }

    // returns the ID of the user
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
