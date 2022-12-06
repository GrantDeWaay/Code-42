package coms309.coderunner;

import coms309.api.dataobjects.ApiAssignmentUnitTest;

public class AssignmentUnitTestResult {
    
    private ApiAssignmentUnitTest unitTest;

    private String actualOutput;

    private String message;

    private boolean passed;

    public AssignmentUnitTestResult(ApiAssignmentUnitTest unitTest, String actualOutput, String message, boolean passed) {
        this.unitTest = unitTest;
        this.actualOutput = actualOutput;
        this.message = message;
        this.passed = passed;
    }

    public void setUnitTest(ApiAssignmentUnitTest unitTest) {
        this.unitTest = unitTest;
    }

    public ApiAssignmentUnitTest getUnitTest() {
        return unitTest;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isPassed() {
        return passed;
    }

}
