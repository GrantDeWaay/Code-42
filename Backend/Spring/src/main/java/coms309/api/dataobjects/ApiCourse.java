package coms309.api.dataobjects;

import java.util.Date;

import coms309.database.dataobjects.Course;
import io.swagger.annotations.ApiModelProperty;

public class ApiCourse {

    /**
     * Unique integer ID for each course.
     */
    @ApiModelProperty(notes = "Unique ID of the Course", name = "id", required = true, value = "1")
    private Long id;

    /**
     * Title of course.
     */
    @ApiModelProperty(notes = "Title of the Course", name = "title", required = true, value = "Example Course")
    private String title;

    /**
     * Brief description of course.
     */
    @ApiModelProperty(notes = "Description of the Course", name = "description", required = true, value = "Example Description")
    private String description;

    /**
     * Programming languages used in the course.
     */
    @ApiModelProperty(notes = "Languages for the Course, comma delimited", name = "languages", required = true, value = "C")
    private String languages;

    /**
     * Creation date of course
     */
    @ApiModelProperty(notes = "Creation date of the Course", name = "creationDate", required = true, value = "1970-01-01T00:00:00.00")
    private Date creationDate;

    /**
     * Default constructor.
     * All fields initialize to null.
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
     * @param id           id for the course
     * @param title        title for the course
     * @param description  description for the course
     * @param languages    languages used in the course
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
     * Get the unique id for the course.
     *
     * @return Long
     */
    public Long getId() {
        return id;
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
     * Get the title for the course.
     *
     * @return String
     */
    public String getTitle() {
        return title;
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
     * Get the description for the course.
     *
     * @return String
     */
    public String getDescription() {
        return description;
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
     * Get the languages for the course.
     *
     * @return String
     */
    public String getLanguages() {
        return languages;
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
     * Check if the course uses a given language.
     *
     * @param language language to check
     * @return true if the language is used, false otherwise
     */
    public boolean usesLanguage(String language) {
        String[] languageArray = languages.split(",");

        for (String s : languageArray) {
            if (s.equals(language)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the creation date of the course.
     *
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date of the course.
     *
     * @param creationDate new creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
