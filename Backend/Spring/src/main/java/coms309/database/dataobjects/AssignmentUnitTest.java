package coms309.database.dataobjects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assignment_unit_tests")
public class AssignmentUnitTest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // input that will be supplied to the process as a part of the unit test
    private String input;

    // expected output from the process
    private String expectedOutput;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "assignment_id", nullable = true)
    private Assignment assignment;

    public AssignmentUnitTest() {

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

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

}
