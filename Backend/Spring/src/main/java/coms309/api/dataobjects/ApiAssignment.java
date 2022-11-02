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

    private String template;

    private String expectedOutput;

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
        this.template = a.getTemplate();
        this.expectedOutput = a.getExpectedOutput();
    }

    
    /** 
     * @param id
     */
    public void setId(Long id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
    }

    
    /** 
     * @return Long
     */
    // returns the ID of the user
    public Long getId() {
        return id;
    }

    
    /** 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /** 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param problemStatement
     */
    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    
    /** 
     * @return String
     */
    public String getProblemStatement() {
        return problemStatement;
    }

    
    /** 
     * @param template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    
    /** 
     * @return String
     */
    public String getTemplate() {
        return template;
    }

    
    /** 
     * @param expectedOutput
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    
    /** 
     * @return String
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    
    /** 
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    /** 
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    
    /** 
     * @param dueDate
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    
    /** 
     * @return Date
     */
    public Date getDueDate() {
        return dueDate;
    }

}

