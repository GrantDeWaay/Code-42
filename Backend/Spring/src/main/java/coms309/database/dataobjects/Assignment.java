package coms309.database.dataobjects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import coms309.api.dataobjects.*;

import javax.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // title of assignment
    private String title;

    // description of assignment
    private String description;

    // problem statement for coding challenge
    private String problemStatement;

    private String template;

    private String expectedOutput;

    private Date creationDate;

    // date assignment is due
    private Date dueDate;

    private String language;

    // course this assignment belongs to
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    // grades for everyone that did the assignment
    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Grade> grades = new HashSet<>();

    @OneToOne(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AssignmentFile assignmentFile;

    public Assignment() {

    }

    public Assignment(ApiAssignment a) {
        this.id = a.getId();
        this.title = a.getTitle();
        this.description = a.getDescription();
        this.problemStatement = a.getProblemStatement();
        this.creationDate = a.getCreationDate();
        this.dueDate = a.getDueDate();
        this.template = a.getTemplate();
        this.expectedOutput = a.getExpectedOutput();
    }

    // returns the ID of the user
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public AssignmentFile getAssignmentFile() {
        return assignmentFile;
    }

    public void setAssignmentFile(AssignmentFile assignmentFile) {
        this.assignmentFile = assignmentFile;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
