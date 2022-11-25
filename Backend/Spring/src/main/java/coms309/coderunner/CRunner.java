package coms309.coderunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;

public class CRunner extends CodeRunner {

    private String mainName;
    
    /**
     * Constructor for C Runner.
     * 
     * @param af AssignmentFile object for use with files (should not be null, can just pass new AssignmentFile() if none available)
     * @param acs ApiCodeSubmission object to get contents of submission to run
     * @param tfm TempFileManager object to manage temporary directory for executing file
     * @throws IOException
     */
    public CRunner(AssignmentFile af, ApiCodeSubmission acs, TempFileManager tfm) throws IOException {
        super(af.getCodeFolder(), tfm.getAssignmentFolderPath());

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

        ProcessBuilder processBuilder;
        
        if(codeFolder.equals("")) {
            // no compile script, just compile a single file
            String executableName = mainName.substring(0, mainName.indexOf('.'));
            File outDir = new File(testFolder + "/out/");
            outDir.mkdirs();
            processBuilder = new ProcessBuilder("gcc", testFolder + "/" + mainName, "-o", testFolder + "/out/" + executableName);
        } else {
            processBuilder = new ProcessBuilder(testFolder + "/compile.sh" + mainName);
        }

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
     * Method inherited from CodeRunner.  Runs the compiled C program and saves the output/error streams so they can be checked later.
     * 
     * @return true if process runs successfully in allotted time with exit code 0, false otherwise
     * @throws IOException
     */
    @Override
    public boolean run() throws IOException {
        String executableName = mainName.substring(0, mainName.indexOf('.'));

        ProcessManager processManager = new ProcessManager(new ProcessBuilder(testFolder + "/out/" + executableName));

        if(!processManager.runForTime(maxRuntime)) processManager.terminateProcess();

        stdOutData = processManager.getOutputData();
        stdErrData = processManager.getErrorData();

        return true;

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
