package coms309.coderunner;

import java.io.IOException;

public abstract class CompiledCodeRunner extends CodeRunner {
    
    public CompiledCodeRunner(String codeFolder, String testFolder) {
        super(codeFolder, testFolder);
    }

    // compiles the code
    // returns true on compilation success, false on failure
    public abstract boolean compile() throws IOException;

}
