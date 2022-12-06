package coms309.api.dataobjects;

import coms309.database.dataobjects.AssignmentUnitTest;

public class ApiAssignmentUnitTest {
    
    private Long id;

    // input that will be supplied to the process as a part of the unit test
    private String input;

    // expected output from the process
    private String expectedOutput;

    public ApiAssignmentUnitTest() {

    }

    public ApiAssignmentUnitTest(AssignmentUnitTest assignmentUnitTest) {
        this.id = assignmentUnitTest.getId();
        this.input = assignmentUnitTest.getInput();
        this.expectedOutput = assignmentUnitTest.getExpectedOutput();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

}
