package coms309.database.repository;

import coms309.database.dataobjects.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
