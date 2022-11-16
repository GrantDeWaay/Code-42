package coms309.api.dataobjects;

import java.util.Date;

import coms309.database.dataobjects.Assignment;
import io.swagger.annotations.ApiModelProperty;

/**
 * Minimized version of Assignment object used in database.
 * Class is missing references to other data objects.
 * Used as responses for API calls.
 */
public class ApiAssignment {

    /**
     * Unique id for the assignment.
     */
    @ApiModelProperty(value = "Unique ID of the Assignment", name = "id", required = true)
    private Long id;

    /**
     * Title of assignment.
     */
    @ApiModelProperty(value = "Title of the Assignment", name = "title", required = true)
    private String title;

    /**
     * Description of assignment.
     */
    @ApiModelProperty(value = "Description of the Assignment", name = "description", required = true)
    private String description;

    /**
     * Problem statement for coding challenge.
     */
    @ApiModelProperty(value = "Problem Statement for the Assignment", name = "problemStatement", required = true)
    private String problemStatement;

    /**
     * Code template containing boilerplate code given by teacher.
     */
    @ApiModelProperty(value = "Template code for the Assignment, sent to user to complete", name = "template", required = true)
    private String template;

    /**
     * Expected output from assignment in plaintext.
     */
    @ApiModelProperty(value = "Expected output for the Assignment that is checked against generated output", name = "expectedOutput", required = true)
    private String expectedOutput;

    /**
     * Date when the assignment was created.
     */
    @ApiModelProperty(value = "Creation date of the Assignment", name = "creationDate", required = true)
    private Date creationDate;

    /**
     * Date assignment is due.
     */
    @ApiModelProperty(value = "Due date of the Assignment", name = "dueDate", required = true)
    private Date dueDate;

    /**
     * Default constructor, all fields initialize to null.
     */
    public ApiAssignment() {

    }

    /**
     * Create an ApiAssignment from a database Assignment.
     * Used when returning info to clients.
     *
     * @param a Assignment object that is stored in database
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
     * Get the id for this object.
     *
     * @return Long
     */
    public Long getId() {
        return id;
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
     * Get the title of the assignment.
     *
     * @return String
     */
    public String getTitle() {
        return title;
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
     * Get the description for the assignment.
     *
     * @return String
     */
    public String getDescription() {
        return description;
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
     * Get the problem statement for the assignment.
     *
     * @return String
     */
    public String getProblemStatement() {
        return problemStatement;
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
     * Get the template code for the assignment.
     *
     * @return String
     */
    public String getTemplate() {
        return template;
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
     * Get the expected output for the assignment.
     *
     * @return String
     */
    public String getExpectedOutput() {
        return expectedOutput;
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
     * Get the creation date for the assignment.
     *
     * @return Date
     */
    public Date getCreationDate() {
        return creationDate;
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
     * Get the due date for the assignment.
     *
     * @return Date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date for the assignment.
     *
     * @param dueDate new due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}

