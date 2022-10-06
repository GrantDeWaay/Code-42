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

    public ApiGrade() {

    }

    public ApiGrade(Grade g) {
        this.id = g.getId();
        this.grade = g.getGrade();
        this.updateDate = g.getUpdateDate();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getGrade() {
        return grade;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

}