package coms309.coderunner;

import coms309.api.dataobjects.ApiAssignmentUnitTest;
import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.dataobjects.AssignmentUnitTest;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Process process = Runtime.getRuntime().exec("go build -o " + testFolder + "/out " + testFolder + "/" + fileName);
        try {
            if (process.waitFor(5, TimeUnit.SECONDS)) {
                return process.exitValue() == 0;
            }
        } catch (InterruptedException e) {
            return false;
        }
        return false;
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