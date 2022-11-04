package coms309.coderunner;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class JavaRunner extends CodeRunner {

    private String fileName;

    public JavaRunner(AssignmentFile af, ApiCodeSubmission acs, TempFileManager tfm) throws FileNotFoundException {
        super(af.getCodeFolder(), tfm.getAssignmentFolderPath());
        compiledRunner = true;
        this.fileName = acs.getName();
    }

    @Override
    public boolean compile() throws IOException {
        Process process = Runtime.getRuntime().exec("javac " + testFolder + "/" + fileName);

        try {
            if (process.waitFor(5, TimeUnit.SECONDS)) {
                return process.exitValue() == 0;
            }
        } catch (InterruptedException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean run() throws IOException {
        String className = fileName.substring(0, fileName.indexOf('.'));
        Process process = Runtime.getRuntime().exec("java " + testFolder + "/" + className);

        stdout = process.getInputStream();

        long startTime = System.currentTimeMillis();

        byte[] buff = new byte[1024];

        while(process.isAlive() && System.currentTimeMillis() - startTime < maxRuntime) {
            // Waiting
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            stdOutData += line;
        }

        if (process.isAlive()) {
            process.destroyForcibly();
            return false;
        }

        return true;
    }

}
