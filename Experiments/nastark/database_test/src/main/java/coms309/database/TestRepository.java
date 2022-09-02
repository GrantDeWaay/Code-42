package coms309.database;

import org.springframework.data.repository.CrudRepository;

import coms309.database.Person;

public interface TestRepository extends CrudRepository<Person, Integer> {
    
}
