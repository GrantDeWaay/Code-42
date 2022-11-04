package coms309.coderunner;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        stderr = process.getErrorStream();

        long startTime = System.currentTimeMillis();

        byte[] buff = new byte[1024];

        while (process.isAlive() && System.currentTimeMillis() - startTime < maxRuntime) {
            while (stdout.available() > 0) {
                int n = stdout.read(buff);
                stdOutData = stdOutData.concat(new String(buff, 0, n));
            }

            while (stderr.available() > 0) {
                int n = stderr.read(buff);
                stdErrData = stdErrData.concat(new String(buff, 0, n));
            }
        }

        if (process.isAlive()) {
            process.destroyForcibly();
            return false;
        }

        return true;
    }

}
