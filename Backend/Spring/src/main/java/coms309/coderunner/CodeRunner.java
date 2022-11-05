package coms309.coderunner;

import java.io.IOException;
import java.io.InputStream;

import javax.transaction.NotSupportedException;

public abstract class CodeRunner {

    // whether or not this runner needs to be compiled
    protected boolean compiledRunner;

    // path to folder on VM disk that contains skeleton code for assignment
    protected String codeFolder;

    // folder that the code will be run in
    protected String testFolder;

    protected InputStream stdout;

    protected InputStream stderr;

    // all data written to stdout during program execution
    protected String stdOutData;

    // all data written to stderr during program execution
    protected String stdErrData;

    // maximum time that a program can run for, in milliseconds
    protected long maxRuntime;

    protected CodeRunner(String codeFolder, String testFolder) {
        this.codeFolder = codeFolder;
        this.testFolder = testFolder;
        this.stdOutData = "";
        this.stdErrData = "";
        this.maxRuntime = 5000; // default to 5 seconds
    }
    
    /**
     * Checks whether this runner needs to be compiled before being run.
     * 
     * @see compile()
     * 
     * @return true if runner is for a compiled language, false otherwise
     */
    public boolean isCompiledRunner() {
        return compiledRunner;
    }


    /** 
     * Gets the path to the template code folder.
     * 
     * @return path to template code folder
     */
    public String getCodeFolder() {
        return codeFolder;
    }

    
    /** 
     * Gets the path to the temporary testing folder.
     * 
     * @return path to temporary testing folder
     */
    public String getTestFolder() {
        return testFolder;
    }

    
    /** 
     * After run() has been called, gets the output from the program.
     * 
     * @return data written to stdout when program is run, if any
     */
    public String getStdOutData() {
        return stdOutData;
    }

    
    /** 
     * After run() has been called, gets the error output from the program.
     * 
     * @return data written to stderr when program is run, if any
     */
    public String getStdErrData() {
        return stdErrData;
    }

    
    /**
     * Sets the maximum runtime for the program being run by the runner.
     * 
     * @param maxRuntime maximum runtime in milliseconds
     */
    public void setMaxRuntime(long maxRuntime) {
        this.maxRuntime = maxRuntime;
    }


    /**
     * Gets the maximum runtime for the program being run by the runner.
     * 
     * @return the maximum runtime in milliseconds
     */
    public long getMaxRuntime() {
        return maxRuntime;
    }

    /**
     * Abstract method to be implemented by child class, runs the code.
     * 
     * @return true if the process exits with code 0 in the allotted run time
     * @throws IOException if an IO error occurs during runtime
     */
    public abstract boolean run() throws IOException;


    /**
     * Abstract method to be implemented by child class, compiles the code (compiled languages only).
     * 
     * @return true if compilation succeeds, false otherwise
     * @throws IOException if an IO error occurs during compilation
     * @throws NotSupportedException if the runner doesn't support compilation (e.x. an interpreted language)
     */
    public abstract boolean compile() throws IOException, NotSupportedException;

}