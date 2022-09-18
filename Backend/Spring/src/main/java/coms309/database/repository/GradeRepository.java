package coms309.database.repository;

import coms309.database.dataobjects.Grade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    
}
