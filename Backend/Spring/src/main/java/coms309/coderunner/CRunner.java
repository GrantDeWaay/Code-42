package coms309.coderunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class CRunner extends CompiledCodeRunner {

    private String mainName;
    
    public CRunner(String codeFolder, String testFolder, String mainName) throws IOException {
        super(codeFolder, testFolder);

        this.mainName = mainName;     
        
        copyDirRecursively(codeFolder, testFolder);
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

    // helper function
    private static void copyDirRecursively(String src, String dest) throws IOException {
        File srcFile = new File(src);

        for(File f : srcFile.listFiles()) {
            if(f.isDirectory()) {
                copyDirRecursively(f.getAbsolutePath(), dest + "/" + f.getName());
            } else {
                Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(dest + "/" + f.getName()));
            }
        }
    }

}
