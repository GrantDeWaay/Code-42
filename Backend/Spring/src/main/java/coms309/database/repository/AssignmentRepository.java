package coms309.database.repository;

import coms309.database.dataobjects.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
}
