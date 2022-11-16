package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.Course;
import coms309.database.repository.CourseRepository;

/**
 * @author Nathan Stark
 * 
 * @see RepositoryService
 * 
 * The CourseService class is one of several that implements the RepositoryService interface.  This service contains all methods to interact with the Course repository
 * from the API.
 */
@Service
public class CourseService implements RepositoryService<Course> {

    @Autowired
    CourseRepository repository;

    
    /** 
     * Overridden interface method.  Finds an Course by integer ID.
     * 
     * @see RepositoryService
     * 
     * @param id id of Course to find
     * @return Optional<Course> containing the Course if it exists, or empty if it does not
     */
    @Override
    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    
    /** 
     * Overridden interface method.  Returns a list of all Courses in the repository.
     * 
     * @see RepositoryService
     * 
     * @return List<Course> of all Courses in repository
     */
    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    
    /** 
     * Overridden interface method.  Saves an Course object in the repository.  Currently the same as update, but has a different name to make writing
     * code easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see update
     * 
     * @param t Course object to add to repository
     * @return Course object that was added to the repository
     */
    @Override
    public Course create(Course t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Saves an Course object in the repository.  Currently the same as create, but has a different name to make writing code
     * easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see create
     * 
     * @param t Course to update in the repository
     * @return Course object that was updated
     */
    @Override
    public Course update(Course t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Deletes the Course object with the specified ID from the repository.
     * 
     * @see RepositoryService
     * 
     * @param id ID of Course to delete
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
     * @param id ID of Course to search for
     * @return boolean true if an object with the ID exists, false otherwise
     */
    @Override
    public boolean contains(Long id) {
        return repository.existsById(id);
    }

    
    /** 
     * Overridden interface method.  Returns a count of the number of Course objects stored in the repository.
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
