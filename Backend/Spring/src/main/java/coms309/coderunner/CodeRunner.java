package coms309.coderunner;

import java.io.IOException;

public abstract class CodeRunner {

    // path to folder on VM disk that contains skeleton code for assignment
    protected String codeFolder;

    // folder that the code will be run in
    protected String testFolder;

    protected CodeRunner(String codeFolder, String testFolder) {
        this.codeFolder = codeFolder;
        this.testFolder = testFolder;
    }
    
    public String getCodeFolder() {
        return codeFolder;
    }

    public String getTestFolder() {
        return testFolder;
    }

    // run the code
    public abstract boolean run() throws IOException;

}
