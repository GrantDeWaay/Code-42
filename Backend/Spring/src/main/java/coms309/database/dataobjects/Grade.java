package coms309.database.dataobjects;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {
    
    // ID of the grade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO see if we just want this as percents or include points as well

    // grade as a decimal value
    private Double grade;

    // date the grade was last updated
    private Date updateDate;

    // assignment the grade belongs to
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "assignment_id", nullable = true)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "student_id", nullable = true)
    private User user;

    public Grade() {

    }

    public Grade(Double grade, Date updateDate) {
        this.grade = grade;
        this.updateDate = updateDate;
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

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
