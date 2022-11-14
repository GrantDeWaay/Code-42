package coms309.api.dataobjects;

public class ApiCodeSubmission {

    /**
     * Name of file that will be compiled.
     * This includes file extension.
     */
    private String name;

    /**
     * Code being compiled.
     * Each line will be seperated by a newline
     */
    private String contents;

    /**
     * Name of coding language
     */
    private String language;

    /**
     * Default constructor.
     * All fields initialize to null.
     */
    public ApiCodeSubmission() {

    }

    /**
     * Constructor.
     *
     * @param name     name of file being submitted
     * @param contents contents of the file
     * @param language language of the file (C, Java, etc.)
     */
    public ApiCodeSubmission(String name, String contents, String language) {
        this.name = name;
        this.contents = contents;
        this.language = language;
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
     * Set the name of the code submission.
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
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
     * Set the contents of the code submission.
     *
     * @param contents new contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * Get the language for the code submission.
     *
     * @return String
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set the language for the code submission.
     *
     * @param language new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
