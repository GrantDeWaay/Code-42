package coms309.api.dataobjects;

import java.util.Date;

import coms309.database.dataobjects.Course;

public class ApiCourse {
    
    // unique integer ID for each course
    private Long id;

    // title of course
    private String title;

    // brief description of course
    private String description;

    // programming languages used in the course
    private String languages;

    // creation date of course
    private Date creationDate;
    
    public ApiCourse() {

    }

    public ApiCourse(Course c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.description = c.getDescription();
        this.languages = c.getLanguages();
        this.creationDate = c.getCreationDate();
    }

    public ApiCourse(Long id, String title, String description, String languages, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.languages = languages;
        this.creationDate = creationDate;
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
