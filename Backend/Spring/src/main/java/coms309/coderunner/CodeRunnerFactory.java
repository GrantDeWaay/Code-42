package coms309.coderunner;

import java.io.IOException;

public class CodeRunnerFactory {
    
    public CompiledCodeRunner createCompiledCodeRunner(String codeFolder, String testFolder, String mainName, String language) {
        if(language == null || language.isEmpty()) return null;

        try {
            switch(language) {
                case "C":
                    return new CRunner(codeFolder, testFolder, mainName);

                default:
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
