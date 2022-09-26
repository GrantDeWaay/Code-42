package coms309.database.dataobjects;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Grade {
    
    // ID of student the grade was issued for
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    // ID of the assigment the grade was for
    private Long assignmentId;

    // TODO see if we just want this as percents or include points as well

    // grade as a decimal value
    private Double grade;

    // date the grade was last updated
    private Date updateDate;

    // assignment the grade belongs to
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    public Grade() {

    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setAssigmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getAssigmeId() {
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
