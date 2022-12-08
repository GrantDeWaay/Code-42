package coms309.coderunner;

import coms309.api.dataobjects.ApiAssignmentUnitTest;
import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.dataobjects.AssignmentUnitTest;

import java.io.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class GoRunner extends CodeRunner {

    private String fileName;

    /**
     * Constructor.  Creates a new Go Runner with the given parameters.
     * 
     * @param af AssignmentFile object for use with files (should not be null, can just pass new AssignmentFile() if none available)
     * @param acs ApiCodeSubmission object to get contents of submission to run
     * @param tfm TempFileManager object to manage temporary directory for executing file
     * @throws FileNotFoundException
     */
    public GoRunner(AssignmentFile af, ApiCodeSubmission acs, TempFileManager tfm, Iterable<AssignmentUnitTest> unitTests) throws FileNotFoundException {
        super(af.getCodeFolder(), tfm.getAssignmentFolderPath(), unitTests);
        compiledRunner = true;
        this.fileName = acs.getName();
    }

    
    /** 
     * Method inherited from CompiledCodeRunner.  Compiles the Go program.
     * 
     * @return true on compilation success, false on failure
     * @throws IOException
     */
    @Override
    public boolean compile() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("go", "build", "-o", testFolder + "/out", testFolder + "/" + fileName);

        ProcessManager processManager = new ProcessManager(processBuilder);
        if(processManager.runForTime(5000)) {
            stdOutData = processManager.getOutputData();
            stdErrData = processManager.getErrorData();
            return processManager.getExitValue() == 0;
        }
        
        processManager.terminateProcess();
        
        stdOutData = processManager.getOutputData();
        stdErrData = processManager.getErrorData();

        return false;
    }

    /**
     * Method inherited from CodeRunner.  Runs the compiled Go program against the next unit test from the list, if available.
     */
    public AssignmentUnitTestResult runNext() throws IOException, NoSuchElementException {
        if(!curTest.hasNext()) throw new NoSuchElementException("No more unit tests exist");

        stdOutData = "";
        stdErrData = "";

        String executableName = fileName.substring(0, fileName.indexOf('.'));

        Process process = Runtime.getRuntime().exec(testFolder + "/out/" + executableName);

        stdin = process.getOutputStream();
        stdout = process.getInputStream();
        stderr = process.getErrorStream();

        long startTime = System.currentTimeMillis();

        byte[] buff = new byte[1024];

        AssignmentUnitTest aut = curTest.next();

        stdin.write(aut.getInput().getBytes());
        stdin.flush();

        // run while process is alive and we have not hit a timeout
        while(process.isAlive() && System.currentTimeMillis() - startTime < maxRuntime) {
            while(stdout.available() > 0) {
                int n = stdout.read(buff);
                stdOutData = stdOutData.concat(new String(buff, 0, n));
            }

            while(stderr.available() > 0) {
                int n = stderr.read(buff);
                stdErrData = stdErrData.concat(new String(buff, 0, n));
            }
        }

        if(process.isAlive()){
            process.destroyForcibly();
        }

        return new AssignmentUnitTestResult(new ApiAssignmentUnitTest(aut), stdOutData, stdErrData, stdOutData.equals(aut.getExpectedOutput()));
    }

    
    /** 
     * Method inherited from CodeRunner.  Runs the compiled Go program and saves the output/error streams so they can be checked later.
     * 
     * @return true if process runs successfully in allotted time with exit code 0, false otherwise
     * @throws IOException
     */
    @Override
    public List<AssignmentUnitTestResult> run() throws IOException {
        String executableName = fileName.substring(0, fileName.indexOf('.'));

        List<AssignmentUnitTestResult> results = new LinkedList<>();

        for (AssignmentUnitTest aut : unitTests) {

            Process process = Runtime.getRuntime().exec(testFolder + "/out/" + executableName);

            stdin = process.getOutputStream();
            stdout = process.getInputStream();
            stderr = process.getErrorStream();

            long startTime = System.currentTimeMillis();

            byte[] buff = new byte[1024];

            stdin.write(aut.getInput().getBytes());
            stdin.flush();

            // run while process is alive and we have not hit a timeout
            while(process.isAlive() && System.currentTimeMillis() - startTime < maxRuntime) {
                while(stdout.available() > 0) {
                    int n = stdout.read(buff);
                    stdOutData = stdOutData.concat(new String(buff, 0, n));
                }

                while(stderr.available() > 0) {
                    int n = stderr.read(buff);
                    stdErrData = stdErrData.concat(new String(buff, 0, n));
                }
            }

            if(process.isAlive()){
                process.destroyForcibly();
            }

            AssignmentUnitTestResult result = new AssignmentUnitTestResult(new ApiAssignmentUnitTest(aut), stdOutData, stdErrData, stdOutData.equals(aut.getExpectedOutput()));
            results.add(result);

            stdOutData = "";
            stdErrData = "";
        }

        return results;
    }

}