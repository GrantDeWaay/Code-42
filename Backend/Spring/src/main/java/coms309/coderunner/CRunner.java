package coms309.coderunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class CRunner extends CompiledCodeRunner {

    private String mainName;
    
    public CRunner(String codeFolder, String testFolder, String mainName) throws IOException {
        super(codeFolder, testFolder);

        this.mainName = mainName;

        Files.copy(Paths.get(codeFolder), Paths.get(testFolder));
    }

    @Override
    public boolean compile() throws IOException {
        Process process = Runtime.getRuntime().exec("./" + testFolder + "/compile.sh " + mainName);
        
        try {
            if(process.waitFor(5, TimeUnit.SECONDS)) {
                return process.exitValue() == 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void run() throws IOException {
        String executableName = mainName.substring(0, mainName.indexOf('.'));

        Process process = Runtime.getRuntime().exec("./" + testFolder + "/out/" + executableName);

        try {
            if(!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroyForcibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
