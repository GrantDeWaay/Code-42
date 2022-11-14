package coms309.api.dataobjects;

public class ApiCodeRunResult {

    /**
     * Whether the result was a success.
     */
    private boolean pass;

    /**
     * Response message to the user containing details on running code.
     */
    private String message;

    /**
     * Expected output of the assignment.
     */
    private String expectedOutput;

    /**
     * What the code actually outputted.
     */
    private String actualOutput;

    /**
     * Constructor.
     *
     * @param pass           whether the code run passed
     * @param message        any message with associated with the run
     * @param expectedOutput expected output for the run
     * @param actualOutput   actual output for the run
     */
    public ApiCodeRunResult(boolean pass, String message, String expectedOutput, String actualOutput) {
        this.pass = pass;
        this.message = message;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
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
     * Set the pass state for the code run.
     *
     * @param pass new pass state
     */
    public void setPass(boolean pass) {
        this.pass = pass;
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
     * Set the message for the code run.
     *
     * @param message new message
     */
    public void setMessage(String message) {
        this.message = message;
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
     * Set the expected output for the code run.
     *
     * @param expectedOutput new expected output
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    /**
     * Get the actual output for the code run.
     *
     * @return String
     */
    public String getActualOutput() {
        return actualOutput;
    }

    /**
     * Set the actual output for the code run.
     *
     * @param actualOutput new actual output
     */
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

}
