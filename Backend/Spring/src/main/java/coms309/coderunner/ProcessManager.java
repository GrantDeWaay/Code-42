package coms309.coderunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessManager {
    
    private ProcessBuilder processBuilder;

    // process being managed
    private Process process;

    // input stream to the process
    private OutputStream stdin;

    // output stream from the process
    private InputStream stdout;

    // error stream from the process
    private InputStream stderr;

    private String outputData = "";

    private String errorData = "";

    /**
     * Constructor, takes a process builder object that is used to create the process.
     * 
     * @param processBuilder
     */
    public ProcessManager(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    
    /** 
     * Start the process.  This will run until the process terminates, or terminateProcess() is called.
     * 
     * @throws IOException
     * 
     * @see terminateProcess
     */
    public void start() throws IOException {
        this.process = processBuilder.start();
        this.stdin = process.getOutputStream();
        this.stdout = process.getInputStream();
        this.stderr = process.getErrorStream();
    }

    
    /** 
     * Starts the process and runs until the process ends or a given number of milliseconds has elapsed, whichever occurs first.
     * 
     * @param milliseconds the maximum number of milliseconds the process is allowed to run
     * @return boolean true if the process ends, false if a time out occurs
     * @throws IOException
     */
    public boolean runForTime(long milliseconds) throws IOException {
        start();

        long startTime = System.currentTimeMillis();

        byte[] buff = new byte[1024];

        while(System.currentTimeMillis() - startTime < milliseconds && process.isAlive()) {
            if(stdout.available() > 0) {
                int n = stdout.read(buff);
                outputData = outputData.concat(new String(buff, 0, n));
            }

            if(stderr.available() > 0) {
                int n = stderr.read(buff);
                errorData = errorData.concat(new String(buff, 0, n));
            }
        }

        return !process.isAlive();
    }

    /**
     * Forcibly ends the process, if it is running
     */
    public void terminateProcess() {
        if(process == null || !process.isAlive()) return;

        process.destroyForcibly();
    }

    public void saveOutput() throws IOException {
        byte[] buff = new byte[1024];

        while(stdout.available() > 0) {
            int n = stdout.read(buff);
            outputData = outputData.concat(new String(buff, 0, n));
        }
    }

    /** 
     * Returns the accumulated output data.  This will automatically be accumulated with either of the run functions.  If using start, saveOutput and saveError must
     * be called.
     * 
     * @return String data from stdout that has been saved
     * 
     * @see saveOutput
     */
    public String getOutputData() {
        return outputData;
    }

    
    /** 
     * @return String
     */
    public String getErrorData() {
        return errorData;
    }

    
    /** 
     * @param input
     * @throws IOException
     */
    public void writeInputData(String input) throws IOException {
        stdin.write(input.getBytes());
    }

    /**
     * Get the exit value of the process.
     */
    public int getExitValue() {
        return process.exitValue();
    }

}
