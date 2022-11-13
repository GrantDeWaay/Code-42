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
    
    /**
     * Default constructor.  All fields initialize to null.
     */
    public ApiCourse() {

    }

    /**
     * Constructor.  Creates an ApiCourse from a database course.  Used when returning data to client.
     * 
     * @param c database course to create from
     */
    public ApiCourse(Course c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.description = c.getDescription();
        this.languages = c.getLanguages();
        this.creationDate = c.getCreationDate();
    }

    /**
     * Constructor.  Creates an ApiCourse with set values for all fields.
     * 
     * @param id id for the course
     * @param title title for the course
     * @param description description for the course
     * @param languages languages used in the course
     * @param creationDate creation date of the course
     */
    public ApiCourse(Long id, String title, String description, String languages, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.languages = languages;
        this.creationDate = creationDate;
    }

    
    /** 
     * Set the unique id for the course.
     * 
     * @param id new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * Get the unique id for the course.
     *  
     * @return Long
     */
    public Long getId() {
        return id;
    }

    
    /** 
     * Set the title for the course.
     * 
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /** 
     * Get the title for the course.
     * 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    
    /** 
     * Set the description for the course.
     * 
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * Get the description for the course.
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * Set the languages for the course.  This should be a comma-delimited string.
     * 
     * @param languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    
    /** 
     * Get the languages for the course.
     * 
     * @return String
     */
    public String getLanguages() {
        return languages;
    }

    
    /** 
     * Check if the course uses a given language.
     * 
     * @param language language to check
     * @return true if the language is used, false otherwise
     */
    public boolean usesLanguage(String language) {
        String[] languageArray = languages.split(",");

        for(String s : languageArray) {
            if(s.equals(language)) {
                return true;
            }
        }

        return false;
    }

    
    /** 
     * Set the creation date of the course.
     * 
     * @param creationDate new creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    /** 
     * Get the creation date of the course.
     * 
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

}
