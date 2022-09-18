package coms309.database.dataobjects;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Grade {
    
    // ID of student the grade was issued for
    private Integer studentId;

    // ID of the assigment the grade was for
    private Integer assignmentId;

    // TODO see if we just want this as percents or include points as well

    // grade as a decimal value
    private Double grade;

    // date the grade was last updated
    private Date updateDate;

    public Grade() {

    }
    
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setAssigmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getAssigmeId() {
        return assignmentId;
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
