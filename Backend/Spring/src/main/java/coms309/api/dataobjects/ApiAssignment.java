package coms309.api.dataobjects;

import java.util.Date;

import coms309.database.dataobjects.Assignment;

public class ApiAssignment {

    private Long id;

    // title of assignment
    private String title;

    // description of assignment
    private String description;

    // problem statement for coding challenge
    private String problemStatement;

    // TODO add in sample code (may end up being in database or stored as a file)

    private Date creationDate;

    // date assignment is due
    private Date dueDate;

    public ApiAssignment() {

    }

    public ApiAssignment(Assignment a) {
        this.id = a.getId();
        this.title = a.getTitle();
        this.description = a.getDescription();
        this.problemStatement = a.getProblemStatement();
        this.creationDate = a.getCreationDate();
        this.dueDate = a.getDueDate();
    }

    public void setId(Long id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
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

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

}

