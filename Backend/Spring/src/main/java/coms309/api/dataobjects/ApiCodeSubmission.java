package coms309.api.dataobjects;

public class ApiCodeSubmission {
    
    private String name;

    private String contents;

    private String language;

    public ApiCodeSubmission() {

    }

    public ApiCodeSubmission(String name, String contents, String language) {
        this.name = name;
        this.contents = contents;
        this.language = language;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    
    /** 
     * @return String
     */
    public String getContents() {
        return contents;
    }
    
    
    /** 
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    
    /** 
     * @return String
     */
    public String getLanguage() {
        return language;
    }

}
