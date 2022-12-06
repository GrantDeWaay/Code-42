package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.AssignmentUnitTest;
import coms309.database.repository.AssignmentUnitTestRepository;

/**
 * @author Nathan Stark
 * 
 * @see RepositoryService
 * 
 * The AssignmentService class is one of several that implement the RepositoryService interface.  This service contains all methods to interact with the Assignment
 * repository from the API.
 */
@Service
public class AssignmentUnitTestService implements RepositoryService<AssignmentUnitTest> {

    @Autowired
    AssignmentUnitTestRepository repository;

    
    @Override
    public Optional<AssignmentUnitTest> findById(Long id) {
        return repository.findById(id);
    }

    
    @Override
    public List<AssignmentUnitTest> findAll() {
        return repository.findAll();
    }

    
    @Override
    public AssignmentUnitTest create(AssignmentUnitTest t) {
        return repository.save(t);
    }

    
    @Override
    public AssignmentUnitTest update(AssignmentUnitTest t) {
        return repository.save(t);
    }

    
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    @Override
    public boolean contains(Long id) {
        return repository.existsById(id);
    }

    
    @Override
    public long count() {
        return repository.count();
    }
    
}
