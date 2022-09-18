package coms309.database.dataobjects;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
    
    // unique integer ID for each course
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // title of course
    private String title;

    // brief description of course
    private String description;

    // programming languages used in the course
    private String languages;

    // creation date of course
    private Date creationDate;

    public Course() {

    }

    // returns the ID of the user
    public Long getId() {
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

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    public boolean usesLanguage(String language) {
        String[] languageArray = languages.split(",");

        for(String s : languageArray) {
            if(s.equals(language)) {
                return true;
            }
        }

        return false;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

}
