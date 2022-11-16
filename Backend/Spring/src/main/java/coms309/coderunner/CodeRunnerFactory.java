package coms309.coderunner;

import java.io.IOException;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.database.dataobjects.AssignmentFile;

public class CodeRunnerFactory {


    /**
     * Create a code runner with the given parameters.  Language is determined automatically from the code submission.
     * 
     * @param af
     * @param acs
     * @param tfm
     * @return CompiledCodeRunner
     */
    public CodeRunner createCodeRunner(AssignmentFile af, ApiCodeSubmission acs, TempFileManager tfm) {
        if (acs.getLanguage() == null || acs.getLanguage() == "") return null;

        try {
            switch (acs.getLanguage()) {
                case "C":
                    return new CRunner(af, acs, tfm);
                case "Java":
                    return new JavaRunner(af, acs, tfm);
                case "Go":
                    return new GoRunner(af, acs, tfm);

                default:
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
