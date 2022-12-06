package coms309.database.services;

import java.util.List;
import java.util.Optional;

/**
 * @author Nathan Stark
 * 
 * The RepositoryService interface defines a set of methods that a service wrapping a repository must implement.  Most of these can simply be mapped to repository
 * methods, but extra functionality can be added if desired.
 */
public interface RepositoryService<T> {
    
    /**
     * Find an object by ID.  All tables in the current database schema support this, so this provides a guaranteed way to look up any type of database object.
     * 
     * @param id ID of the object to search for
     * @return Optional<T> containing the object if found, or empty if it does not exist
     */
    Optional<T> findById(Long id);

    /**
     * List all objects in the repository.
     * 
     * @return List<T> of all objects in the repository
     */
    List<T> findAll();

    /**
     * Create an object in the repository.  Similar to update, since both will usually call save at some point.  However, implementations of create should not assume
     * that the object exists, whereas this is a valid assumption for update.
     * 
     * @see update
     * 
     * @param t object to add to the repository
     * @return T the object that was added to the repository
     */
    T create(T t);

    /**
     * Update an object in the repository.  Similar to create, since both will usually call save at some point.  However, implementations of update can assume that the
     * object exists, whereas create cannot.
     * 
     * @param t object in the repository to update
     * @return T the object that was updated in the repository
     */
    T update(T t);

    /**
     * Delete an object from the repository, if it exists.
     * 
     * @param id ID of the object to be deleted
     */
    void delete(Long id);

    /**
     * Check if the repository contains an object with the given ID.  
     * 
     * @param id
     * @return boolean true if the object is in the repository, false otherwise
     */
    boolean contains(Long id);

    /**
     * Get the number of objects contained in the repository.
     * 
     * @return long the number of items in the repository
     */
    long count();

}
