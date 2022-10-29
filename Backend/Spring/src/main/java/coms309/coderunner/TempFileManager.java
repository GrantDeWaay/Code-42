package coms309.coderunner;

import java.io.File;
import java.io.FileNotFoundException;

// class that will manage directory of temporary files for a user's assignments
public class TempFileManager {
    
    // base directory where all temp files are stored
    private File baseDir;

    public TempFileManager(String baseDirPath) {
        this.baseDir = new File(baseDirPath);
        if(!baseDir.isDirectory()) throw new IllegalArgumentException("Invalid path to temp file directory \"" + baseDirPath + "\".");
    }

    // check if user has a folder already made in the directory
    public boolean userFolderExists(Long userId) {
        File[] contents = baseDir.listFiles();

        boolean found = false;

        for(File f : contents) {
            if(f.getName().equals(userId.toString())) found = true;
        }

        return found;
    }

    public boolean assignmentFolderExists(Long userId, Long assignmentId) {
        File userDir = new File(baseDir.getAbsolutePath() + "/" + userId.toString() + "/assignments/");

        File[] contents = userDir.listFiles();

        boolean found = false;

        for(File f : contents) {
            if(f.getName().equals(assignmentId.toString())) found = true;
        }

        return found;
    }

    public void createUserFolder(Long userId) {
        File userDir = new File(baseDir.getAbsolutePath() + "/" + userId.toString());

        userDir.mkdirs();
    }

    public void createAssignmentFolder(Long userId, Long assignmentId) {
        File assignmentDir = new File(baseDir.getAbsolutePath() + "/" + userId.toString() + "/assignments/" + assignmentId.toString());

        assignmentDir.mkdirs();
    }

    public String getUserFolderPath(Long userId) throws FileNotFoundException {
        if(!userFolderExists(userId)) throw new FileNotFoundException("User directory does not exist");

        return baseDir.getAbsolutePath() + "/" + userId.toString();
    }

    public String getAssignmentFolderPath(Long userId, Long assignmentId) throws FileNotFoundException {
        if(!assignmentFolderExists(userId, assignmentId)) throw new FileNotFoundException("Assignment directory does not exist");

        return baseDir.getAbsolutePath() + "/" + userId.toString() + "/assignments/" + assignmentId.toString();
    }

    public String getBaseDirPath() {
        return baseDir.getAbsolutePath();
    }

}
