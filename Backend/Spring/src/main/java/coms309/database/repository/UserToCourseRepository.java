package coms309.database.repository;

import coms309.database.dataobjects.UserToCourseMapping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserToCourseRepository extends JpaRepository<UserToCourseMapping, Long> {
    
}
