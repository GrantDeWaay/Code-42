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

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean getPass() {
        return pass;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }
 
}
