package coms309.coderunner;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;

import java.io.*;

public class GoRunner extends CodeRunner {

    private String fileName;

    public GoRunner(AssignmentFile af, ApiCodeSubmission acs, TempFileManager tfm) throws FileNotFoundException {
        super(af.getCodeFolder(), tfm.getAssignmentFolderPath());
        compiledRunner = true;
        this.fileName = acs.getName();
    }

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

    @Override
    public boolean run() throws IOException {
        String executableName = fileName.substring(0, fileName.indexOf('.'));
        
        ProcessBuilder processBuilder = new ProcessBuilder(testFolder + "/out/" + executableName);
        ProcessManager processManager = new ProcessManager(processBuilder);

        if(processManager.runForTime(maxRuntime)) {
            stdOutData = processManager.getOutputData();
            stdErrData = processManager.getErrorData();

            return processManager.getExitValue() == 0;
        }

        processManager.terminateProcess();

        stdOutData = processManager.getOutputData();
        stdErrData = processManager.getErrorData();

        return false;
    }

}