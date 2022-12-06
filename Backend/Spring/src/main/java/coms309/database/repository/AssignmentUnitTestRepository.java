package coms309.database.repository;

import coms309.database.dataobjects.AssignmentUnitTest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentUnitTestRepository extends JpaRepository<AssignmentUnitTest, Long> {
    
}
