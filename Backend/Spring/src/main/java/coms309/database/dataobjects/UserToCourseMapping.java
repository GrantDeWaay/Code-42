package coms309.database.dataobjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserToCourseMapping {
    
    // ID of user in mapping
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // ID of course in mapping
    private Long courseId;

    public UserToCourseMapping() {

    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }
}
