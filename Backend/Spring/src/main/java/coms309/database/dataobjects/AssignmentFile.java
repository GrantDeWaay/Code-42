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

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCodeFolder(String codeFolder) {
        this.codeFolder = codeFolder;
    }

    public String getCodeFolder() {
        return codeFolder;
    }

    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }

    public String getMainFile() {
        return mainFile;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

}
