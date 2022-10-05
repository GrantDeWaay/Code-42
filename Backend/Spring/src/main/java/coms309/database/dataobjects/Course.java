package coms309.database.dataobjects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    
    // unique integer ID for each course
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // title of course
    private String title;

    // brief description of course
    private String description;

    // programming languages used in the course
    private String languages;

    // creation date of course
    private Date creationDate;

    // set of all students enrolled in the course
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<User> students = new HashSet<>();

    // set of all assignments in course
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Assignment> assignments = new HashSet<>();
    
    public Course() {

    }

    public Course(Long id, String title, String description, String languages, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.languages = languages;
        this.creationDate = creationDate;
    }

    // returns the ID of the user
    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getLanguages() {
        return languages;
    }

    public boolean usesLanguage(String language) {
        String[] languageArray = languages.split(",");

        for(String s : languageArray) {
            if(s.equals(language)) {
                return true;
            }
        }

        return false;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Set<User> getStudents() {
        return students;
    }

}
