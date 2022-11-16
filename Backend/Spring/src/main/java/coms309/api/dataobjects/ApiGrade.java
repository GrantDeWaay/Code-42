package coms309.api.dataobjects;

import coms309.database.dataobjects.Grade;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ApiGrade {

    /**
     * id of the grade
     */
    @ApiModelProperty(value = "Unique ID for the Grade", name = "id", required = true)
    private Long id;

    /**
     * Grade as a decimal value.
     */
    @ApiModelProperty(value = "Percentage grade for the Grade", name = "grade", required = true)
    private Double grade;

    /**
     * Date the grade was last updated.
     */
    @ApiModelProperty(value = "Last time the Grade was updated", name = "updateDate", required = true)
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
     * Get the unique id for this grade.
     *
     * @return Long
     */
    public Long getId() {
        return id;
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
     * Get the grade.
     *
     * @return Double
     */
    public Double getGrade() {
        return grade;
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
     * Get the update date for the grade.
     *
     * @return Date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Set the update date for the grade.
     *
     * @param updateDate new update date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}