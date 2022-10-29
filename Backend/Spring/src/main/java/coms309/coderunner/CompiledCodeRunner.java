package coms309.coderunner;

public abstract class CompiledCodeRunner extends CodeRunner {
    
    public CompiledCodeRunner(String codeFolder, String testFolder) {
        super(codeFolder, testFolder);
    }

    // compiles the code
    public abstract void compile();

}
