package coms309.coderunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import coms309.api.dataobjects.ApiAssignmentUnitTest;
import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.dataobjects.AssignmentUnitTest;

public class CRunner extends CodeRunner {

    private String mainName;
    
    /**
     * Constructor.  Creates a new C Runner with the given parameters.
     * 
     * @param af AssignmentFile object for use with files (should not be null, can just pass new AssignmentFile() if none available)
     * @param acs ApiCodeSubmission object to get contents of submission to run
     * @param tfm TempFileManager object to manage temporary directory for executing file
     * @throws IOException
     */
    public CRunner(AssignmentFile af, ApiCodeSubmission acs, TempFileManager tfm, Iterable<AssignmentUnitTest> unitTests) throws IOException {
        super(af.getCodeFolder(), tfm.getAssignmentFolderPath(), unitTests);

        compiledRunner = true;

        this.mainName = acs.getName();
        
        copyDirRecursively(codeFolder, testFolder);
    }

    
    /** 
     * Method inherited from CompiledCodeRunner.  Compiles the C program using a script (for multiple file programs) or with gcc directly (for single files).
     * 
     * @return true on compilation success, false on failure
     * @throws IOException
     */
    @Override
    public boolean compile() throws IOException {

        Process process;
        
        if(codeFolder.equals("")) {
            // no compile script, just compile a single file
            String executableName = mainName.substring(0, mainName.indexOf('.'));
            File outDir = new File(testFolder + "/out/");
            outDir.mkdirs();
            process = Runtime.getRuntime().exec("gcc " + testFolder + "/" + mainName + " -o " + testFolder + "/out/" + executableName);
        } else {
            process = Runtime.getRuntime().exec(testFolder + "/compile.sh " + mainName);
        }
        
        try {
            if(process.waitFor(5, TimeUnit.SECONDS)) {
                return process.exitValue() == 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    
    /** 
     * Method inherited from CodeRunner.  Runs the compiled C program and saves the output/error streams so they can be checked later.
     * 
     * @return true if process runs successfully in allotted time with exit code 0, false otherwise
     * @throws IOException
     */
    @Override
    public Iterable<AssignmentUnitTestResult> run() throws IOException {
        String executableName = mainName.substring(0, mainName.indexOf('.'));

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

    
    /** 
     * Helper function to copy over a template directory to a given destination.  Currently not used as only single files are supported, but may be used in future.
     * 
     * @param src path to source directory
     * @param dest path to destination directory
     * @throws IOException
     */
    private static void copyDirRecursively(String src, String dest) throws IOException {
        File srcFile = new File(src);

        if(!srcFile.exists()) return; // don't copy if no template directory exists

        for(File f : srcFile.listFiles()) {
            if(f.isDirectory()) {
                File dir = new File(dest + "/" + f.getName());
                dir.mkdir();
                copyDirRecursively(f.getAbsolutePath(), dest + "/" + f.getName());
            } else {
                Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(dest + "/" + f.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

}
