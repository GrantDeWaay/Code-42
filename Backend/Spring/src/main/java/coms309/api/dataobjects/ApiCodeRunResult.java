package coms309.api.dataobjects;

public class ApiCodeRunResult {
    
    // whether or not the result was a success
    private boolean pass;

    // message to user (e.x. if compilation or run failed)
    private String message;

    // what the code was supposed to output
    private String expectedOutput;

    // what the code actually outputted
    private String actualOutput;

    /**
     * Constructor.
     * 
     * @param pass whether or not the code run passed
     * @param message any message with associated with the run
     * @param expectedOutput expected output for the run
     * @param actualOutput actual output for the run
     */
    public ApiCodeRunResult(boolean pass, String message, String expectedOutput, String actualOutput) {
        this.pass = pass;
        this.message = message;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

    
    /** 
     * Set the pass state for the code run.
     * 
     * @param pass new pass state
     */
    public void setPass(boolean pass) {
        this.pass = pass;
    }

    
    /** 
     * Get the pass state for the code run.
     * 
     * @return boolean
     */
    public boolean getPass() {
        return pass;
    }
    
    
    /** 
     * Set the message for the code run.
     * 
     * @param message new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
    /** 
     * Get the message for the code run
     * 
     * @return String
     */
    public String getMessage() {
        return message;
    }

    
    /** 
     * Set the expected output for the code run.
     * 
     * @param expectedOutput new expected output
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    
    /** 
     * Get the expected output for the code run.
     * 
     * @return String
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    
    /** 
     * Set the actual output for the code run.
     * 
     * @param actualOutput new actual output
     */
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    
    /** 
     * Get the actual output for the code run.
     * 
     * @return String
     */
    public String getActualOutput() {
        return actualOutput;
    }
 
}
