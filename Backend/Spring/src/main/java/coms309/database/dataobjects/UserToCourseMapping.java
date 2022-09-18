package coms309.database.dataobjects;

import javax.persistence.Entity;

@Entity
public class UserToCourseMapping {
    
    // ID of user in mapping
    private Integer userId;

    // ID of course in mapping
    private Integer courseId;

    public UserToCourseMapping() {

    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseId() {
        return courseId;
    }
}
