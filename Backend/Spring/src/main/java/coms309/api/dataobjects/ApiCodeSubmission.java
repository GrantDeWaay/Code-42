package coms309.api.dataobjects;

public class ApiCodeSubmission {
    
    private String name;

    private String contents;

    public ApiCodeSubmission() {

    }

    public ApiCodeSubmission(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

}
