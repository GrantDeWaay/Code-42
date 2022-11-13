package coms309.api.dataobjects;

import coms309.database.dataobjects.Grade;

import java.util.Date;

public class ApiGrade {
    
    // ID of the grade
    private Long id;

    // TODO see if we just want this as percents or include points as well

    // grade as a decimal value
    private Double grade;

    // date the grade was last updated
    private Date updateDate;

    /**
     * Default constructor.  All fields are initialized to null.
     */
    public ApiGrade() {

    }

    /**
     * Constructor.  Creates a new ApiGrade from a database grade.
     * 
     * @param g database grade to create from
     */
    public ApiGrade(Grade g) {
        this.id = g.getId();
        this.grade = g.getGrade();
        this.updateDate = g.getUpdateDate();
    }

    
    /** 
     * Set the unique id for this grade.
     * 
     * @param id new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /** 
     * Get the unique id for this grade.
     * 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    
    /** 
     * Set the grade (percentage).
     * 
     * @param grade new grade
     */
    public void setGrade(Double grade) {
        this.grade = grade;
    }

    
    /** 
     * Get the grade.
     * 
     * @return Double
     */
    public Double getGrade() {
        return grade;
    }

    
    /** 
     * Set the update date for the grade.
     * 
     * @param updateDate new update date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    
    /** 
     * Get the update date for the grade.
     * 
     * @return Date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

}