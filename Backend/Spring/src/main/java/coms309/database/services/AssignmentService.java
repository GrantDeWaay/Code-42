package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.Assignment;
import coms309.database.repository.AssignmentRepository;

/**
 * @author Nathan Stark
 * 
 * @see RepositoryService
 * 
 * The AssignmentService class is one of several that implement the RepositoryService interface.  This service contains all methods to interact with the Assignment
 * repository from the API.
 */
@Service
public class AssignmentService implements RepositoryService<Assignment> {

    @Autowired
    AssignmentRepository repository;

    
    /** 
     * Overridden interface method.  Finds an assignment by integer ID.
     * 
     * @see RepositoryService
     * 
     * @param id id of Assignment to find
     * @return Optional<Assignment> containing the assignment if it exists, or empty if it does not
     */
    @Override
    public Optional<Assignment> findById(Long id) {
        return repository.findById(id);
    }

    
    /** 
     * Overridden interface method.  Returns a list of all assignments in the repository.
     * 
     * @see RepositoryService
     * 
     * @return List<Assignment> of all assignments in repository
     */
    @Override
    public List<Assignment> findAll() {
        return repository.findAll();
    }

    
    /** 
     * Overridden interface method.  Saves an Assignment object in the repository.  Currently the same as update, but has a different name to make writing
     * code easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see update
     * 
     * @param t Assignment object to add to repository
     * @return Assignment object that was added to the repository
     */
    @Override
    public Assignment create(Assignment t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Saves an Assignment object in the repository.  Currently the same as create, but has a different name to make writing code
     * easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see create
     * 
     * @param t Assignment to update in the repository
     * @return Assignment object that was updated
     */
    @Override
    public Assignment update(Assignment t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Deletes the Assignment object with the specified ID from the repository.
     * 
     * @see RepositoryService
     * 
     * @param id ID of Assignment to delete
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    /** 
     * Overridden interface method.  Checks to see if the repository contains an object with the given ID.
     * 
     * @see RepositoryService
     * 
     * @param id ID of Assignment to search for
     * @return boolean true if an object with the ID exists, false otherwise
     */
    @Override
    public boolean contains(Long id) {
        return repository.existsById(id);
    }

    
    /** 
     * Overridden interface method.  Returns a count of the number of Assignment objects stored in the repository.
     * 
     * @see RepositoryService
     * 
     * @return long number of objects in the repository
     */
    @Override
    public long count() {
        return repository.count();
    }
    
}
