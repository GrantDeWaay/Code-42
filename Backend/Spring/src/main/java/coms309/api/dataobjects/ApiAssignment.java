package coms309.api.dataobjects;

import java.util.Date;

import coms309.database.dataobjects.Assignment;

public class ApiAssignment {

    // unique id for the assignment
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

    /**
     * Default constructor, all fields initialize to null.
     */
    public ApiAssignment() {

    }

    /**
     * Create an ApiAssignment from a database Assignment.  Used when returning info to clients.
     * 
     * @param a
     */
    public ApiAssignment(Assignment a) {
        this.id = a.getId();
        this.title = a.getTitle();
        this.description = a.getDescription();
        this.problemStatement = a.getProblemStatement();
        this.creationDate = a.getCreationDate();
        this.dueDate = a.getDueDate();
        this.template = a.getTemplate();
        this.expectedOutput = a.getExpectedOutput();
    }

    
    /** 
     * Set the id for this object.
     * 
     * @param id new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * Get the id for this object.
     *  
     * @return Long
     */
    public Long getId() {
        return id;
    }

    
    /** 
     * Set the title of the assignment.
     * 
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /** 
     * Get the title of the assignment.
     * 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    
    /** 
     * Set the description for the assignment.
     * 
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * Get the description for the assignment.
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * Set the problem statement for the assignment.
     * 
     * @param problemStatement new problem statement
     */
    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    
    /** 
     * Get the problem statement for the assignment.
     * 
     * @return String
     */
    public String getProblemStatement() {
        return problemStatement;
    }

    
    /** 
     * Set the template code for the assignment.
     * 
     * @param template new template code
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    
    /** 
     * Get the template code for the assignment.
     * 
     * @return String
     */
    public String getTemplate() {
        return template;
    }

    
    /** 
     * Set the expected output for the assignment.
     * 
     * @param expectedOutput new expected output
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    
    /** 
     * Get the expected output for the assignment.
     * 
     * @return String
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    
    /** 
     * Set the creation date for the assignment.
     * 
     * @param creationDate new creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    /** 
     * Get the creation date for the assignment.
     * 
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    
    /** 
     * Set the due date for the assignment.
     * 
     * @param dueDate new due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    
    /** 
     * Get the due date for the assignment.
     * 
     * @return Date
     */
    public Date getDueDate() {
        return dueDate;
    }

}

