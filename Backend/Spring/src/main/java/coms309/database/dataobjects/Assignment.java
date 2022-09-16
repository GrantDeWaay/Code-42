package coms309.database.dataobjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationDescription;
import javax.persistence.Id;

@Entity
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationDescription.IDENTITY)
    private Integer id;

    // title of assignment
    private String title;

    // description of assignment
    private String description;

    // date assignment is due
    private String dueDate;

    // TODO add more fields for location of test files for assignment, base code outline, etc.

    public Assignment() {

    }

    public void setId(Integer id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
    }

    // returns the ID of the user
    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDueDate(String dueDate) {
        // TODO add code to check for valid types
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

}
