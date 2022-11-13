package coms309.api.dataobjects;

public class ApiCodeSubmission {
    
    private String name;

    private String contents;

    private String language;

    /**
     * Default constructor.  All fields initialize to null.
     */
    public ApiCodeSubmission() {

    }

    /**
     * Constructor.
     * 
     * @param name name of file being submitted
     * @param contents contents of the file
     * @param language language of the file (C, Java, etc.)
     */
    public ApiCodeSubmission(String name, String contents, String language) {
        this.name = name;
        this.contents = contents;
        this.language = language;
    }

    
    /** 
     * Set the name of the code submission.
     * 
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * Get the name of the code submission.
     * 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * Set the contents of the code submission.
     * 
     * @param contents new contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    
    /** 
     * Get the contents of the code submission.
     * 
     * @return String
     */
    public String getContents() {
        return contents;
    }
    
    
    /** 
     * Set the language for the code submission.
     * 
     * @param language new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    
    /** 
     * Get the language for the code submission.
     * 
     * @return String
     */
    public String getLanguage() {
        return language;
    }

}
