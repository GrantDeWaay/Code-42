package coms309.coderunner;

import java.io.File;
import java.io.FileNotFoundException;

// class that will manage directory of temporary files for a user's assignments
public class TempFileManager {
    
    // base directory where all temp files are stored
    private File baseDir;

    private Long userId;

    private Long assignmentId;

    /**
     * Constructor.  Create a TempFileManager with the given parameters.
     * 
     * @param baseDirPath base directory path where temp files are stored
     * @param userId user id for the temp files, used to construct path
     * @param assignmentId assignment id for the temp files, used to construct path
     */
    public TempFileManager(String baseDirPath, Long userId, Long assignmentId) {
        this.baseDir = new File(baseDirPath);
        if(!baseDir.isDirectory()) throw new IllegalArgumentException("Invalid path to temp file directory \"" + baseDirPath + "\".");

        this.userId = userId;
        this.assignmentId = assignmentId;
    }

    
    /** 
     * Checks if user already has a temporary file directory under the base directory.
     * 
     * @return true if user folder exists, false otherwise
     */
    public boolean userFolderExists() {
        File[] contents = baseDir.listFiles();

        boolean found = false;

        for(File f : contents) {
            if(f.getName().equals(userId.toString())) found = true;
        }

        return found;
    }

    
    /** 
     * Checks if this assignment already has a folder under the user.
     * 
     * @return true if assignment folder exists, false otherwise
     */
    public boolean assignmentFolderExists() {
        File userDir = new File(baseDir.getAbsolutePath() + "/" + userId.toString() + "/assignments/");

        File[] contents = userDir.listFiles();

        boolean found = false;

        for(File f : contents) {
            if(f.getName().equals(assignmentId.toString())) found = true;
        }

        return found;
    }

    /**
     * Create user folder if it doesn't exist.
     */
    public void createUserFolder() {
        File userDir = new File(baseDir.getAbsolutePath() + "/" + userId.toString());

        userDir.mkdirs();
    }

    /**
     * Create assignment folder for user if it doesn't exist.
     */
    public void createAssignmentFolder() {
        File assignmentDir = new File(baseDir.getAbsolutePath() + "/" + userId.toString() + "/assignments/" + assignmentId.toString());

        assignmentDir.mkdirs();
    }

    
    /** 
     * Get the path to the user temporary folder.
     * 
     * @return String
     * @throws FileNotFoundException
     */
    public String getUserFolderPath() throws FileNotFoundException {
        if(!userFolderExists()) throw new FileNotFoundException("User directory does not exist");

        return baseDir.getAbsolutePath() + "/" + userId.toString();
    }

    
    /** 
     * Get the path to the assignment temporary folder.
     * 
     * @return String
     * @throws FileNotFoundException
     */
    public String getAssignmentFolderPath() throws FileNotFoundException {
        if(!assignmentFolderExists()) throw new FileNotFoundException("Assignment directory does not exist");

        return baseDir.getAbsolutePath() + "/" + userId.toString() + "/assignments/" + assignmentId.toString();
    }

    
    /** 
     * Get the path to the base temporary directory.
     * 
     * @return String
     */
    public String getBaseDirPath() {
        return baseDir.getAbsolutePath();
    }

}
