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

    public ApiCodeRunResult(boolean pass, String message, String expectedOutput, String actualOutput) {
        this.pass = pass;
        this.message = message;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

    
    /** 
     * @param pass
     */
    public void setPass(boolean pass) {
        this.pass = pass;
    }

    
    /** 
     * @return boolean
     */
    public boolean getPass() {
        return pass;
    }
    
    
    /** 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
    /** 
     * @return String
     */
    public String getMessage() {
        return message;
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
     * @param actualOutput
     */
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    
    /** 
     * @return String
     */
    public String getActualOutput() {
        return actualOutput;
    }
 
}
