package coms309.coderunner;

import java.io.IOException;
import java.io.InputStream;

public abstract class CodeRunner {

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

    protected CodeRunner(String codeFolder, String testFolder) {
        this.codeFolder = codeFolder;
        this.testFolder = testFolder;
        this.stdOutData = "";
        this.stdErrData = "";
    }
    
    public String getCodeFolder() {
        return codeFolder;
    }

    public String getTestFolder() {
        return testFolder;
    }

    public String getStdOutData() {
        return stdOutData;
    }

    public String getStdErrData() {
        return stdErrData;
    }

    // run the code
    public abstract boolean run() throws IOException;

}
