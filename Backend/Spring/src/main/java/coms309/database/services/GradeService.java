package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.Grade;
import coms309.database.repository.GradeRepository;

/**
 * @author Nathan Stark
 * 
 * @see RepositoryService
 * 
 * The GradeService class is one of several that implements the RepositoryService interface.  This service contains all methods to interact with the Grade repository
 * from the API.  In addition to the baseline methods provided by RepositoryService, this class also provides an additional method to find a Grade by user and
 * assignment ID.
 */
@Service
public class GradeService implements RepositoryService<Grade> {

    @Autowired
    private GradeRepository repository;

    
    /** 
     * Overridden interface method.  Finds an Grade by integer ID.
     * 
     * @see RepositoryService
     * 
     * @param id id of Grade to find
     * @return Optional<Grade> containing the Grade if it exists, or empty if it does not
     */
    @Override
    public Optional<Grade> findById(Long id) {
        return repository.findById(id);
    }

    
    /** 
     * Overridden interface method.  Returns a list of all Grades in the repository.
     * 
     * @see RepositoryService
     * 
     * @return List<Grade> of all Grades in repository
     */
    @Override
    public List<Grade> findAll() {
        return repository.findAll();
    }

    
    /** 
     * Find a grade based on a user and assignment ID.  This can be used to retrieve the grade that a user got on a specific assignment.
     * 
     * @param userId ID of the user the grade belongs to
     * @param assignmentId ID of the Assignment the grade belongs to
     * @return Grade object that belongs to the given user and assignment IDs, or null if not found
     */
    public Grade findByUserAndAssignment(Long userId, Long assignmentId) {
        List<Grade> grades = findAll();

        for(Grade g : grades) {
            if(g.getUser().getId() == userId && g.getAssignment().getId() == assignmentId) return g;
        }

        return null;
    }

    
    /** 
     * Overridden interface method.  Saves an Grade object in the repository.  Currently the same as update, but has a different name to make writing
     * code easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see update
     * 
     * @param t Grade object to add to repository
     * @return Grade object that was added to the repository
     */
    @Override
    public Grade create(Grade t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Saves an Grade object in the repository.  Currently the same as create, but has a different name to make writing code
     * easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see create
     * 
     * @param t Grade to update in the repository
     * @return Grade object that was updated
     */
    @Override
    public Grade update(Grade t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Deletes the Grade object with the specified ID from the repository.
     * 
     * @see RepositoryService
     * 
     * @param id ID of Grade to delete
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
     * @param id ID of Grade to search for
     * @return boolean true if an object with the ID exists, false otherwise
     */
    @Override
    public boolean contains(Long id) {
        return repository.existsById(id);
    }

    
    /** 
    * Overridden interface method.  Returns a count of the number of Grade objects stored in the repository.
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
