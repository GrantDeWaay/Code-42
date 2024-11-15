package coms309.database.dataobjects;

import javax.persistence.*;

@Entity
@Table(name = "assignment_files")
public class AssignmentFile {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    // path to folder containing assignment skeleton code
    private String codeFolder;

    // name of file containing main method (or other entry point)
    private String mainFile;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "assignment_id", nullable = true)
    private Assignment assignment;

    public AssignmentFile() {
        this.id = Long.valueOf(-1);
        this.codeFolder = "";
        this.mainFile = "";
        this.assignment = null;
    }

    
    /** 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /** 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    
    /** 
     * @param codeFolder
     */
    public void setCodeFolder(String codeFolder) {
        this.codeFolder = codeFolder;
    }

    
    /** 
     * @return String
     */
    public String getCodeFolder() {
        return codeFolder;
    }

    
    /** 
     * @param mainFile
     */
    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }

    
    /** 
     * @return String
     */
    public String getMainFile() {
        return mainFile;
    }

    
    /** 
     * @param assignment
     */
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    
    /** 
     * @return Assignment
     */
    public Assignment getAssignment() {
        return assignment;
    }

}
