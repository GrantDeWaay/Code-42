package coms309.database.dataobjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO see if this can be made abstract?
// data stored for students will be different than teachers, so making them the same doesn't make a lot of sense
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // username associated with user
    private String username;

    // type of user (admin, teacher, etc)
    private String type;

    public User() {

    }

    public void setId(Integer id) {
        // TODO add in code to check IDs for uniqueness
        this.id = id;
    }

    // returns the ID of the user
    public Integer getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setType(String type) {
        // TODO add code to check for valid types
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
