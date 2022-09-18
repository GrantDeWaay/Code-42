package coms309.database.repository;

import coms309.database.dataobjects.UserToCourseMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToCourseRepository extends JpaRepository<UserToCourseMapping, Long> {
    
}
