package coms309.database.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.User;
import coms309.database.repository.UserRepository;

/**
 * @author Nathan Stark
 * 
 * @see RepositoryService
 * 
 * The UserService class is one of several that implements the RepositoryService interface.  This service contains all methods to interact with the User repository
 * from the API.  In addition to the baseline methods provided by RepositoryService, this class also provides additional methods to find Users by username and email,
 * as well as find them by type (student, teacher, admin, etc).
 */
@Service
public class UserService implements RepositoryService<User> {
    
    @Autowired
    private UserRepository repository;

    public UserService() {

    }

    
    /** 
     * Overridden interface method.  Finds an User by integer ID.
     * 
     * @see RepositoryService
     * 
     * @param id id of User to find
     * @return Optional<User> containing the User if it exists, or empty if it does not
     */
    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    
    /** 
     * Finds a User by username.
     * 
     * @param username username of User to search for
     * @return Optional<User> containing the User if it exists, or empty if it does not
     */
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    
    /** 
     * Finds a User by email.
     * 
     * @param email email of User to search for
     * @return Optional<User> containing the User if it exists, or empty if it does not
     */
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    
    /** 
     * Overridden interface method.  Returns a list of all Users in the repository.
     * 
     * @see RepositoryService
     * 
     * @return List<User> of all Users in repository
     */
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    
    /** 
     * Overridden interface method.  Saves an User object in the repository.  Currently the same as update, but has a different name to make writing
     * code easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see update
     * 
     * @param t User object to add to repository
     * @return User object that was added to the repository
     */
    @Override
    public User create(User t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Saves an User object in the repository.  Currently the same as create, but has a different name to make writing code
     * easier with the CRUDL model.
     * 
     * @see RepositoryService
     * 
     * @see create
     * 
     * @param t User to update in the repository
     * @return User object that was updated
     */
    @Override
    public User update(User t) {
        return repository.save(t);
    }

    
    /** 
     * Overridden interface method.  Deletes the User object with the specified ID from the repository.
     * 
     * @see RepositoryService
     * 
     * @param id
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
     * @param id
     * @return boolean true if an object with the ID exists, false otherwise
     */
    @Override
    public boolean contains(Long id) {
        return repository.existsById(id);
    }

    
    /** 
    * Overridden interface method.  Returns a count of the number of User objects stored in the repository.
    * 
    * @see RepositoryService
    * 
    * @return long number of objects in the repository
    */
    @Override
    public long count() {
        return repository.count();
    }

    
    /** 
     * Returns a list of all students in the repository.
     * 
     * @return List<User> of all students in the repository
     */
    public List<User> findAllStudents() {
        LinkedList<User> studentsList = new LinkedList<>();

        List<User> userList = findAll();

        for(User u : userList) {
            if(u.getType().toLowerCase().equals("student")) {
                studentsList.add(u);
            }
        }

        return studentsList;
    }

    
    /** 
     * Returns a list of all teachers in the respository.
     * 
     * @return List<User> of all teachers in the repository
     */
    public List<User> findAllTeachers() {
        LinkedList<User> teachersList = new LinkedList<>();

        List<User> userList = findAll();

        for(User u : userList) {
            if(u.getType().toLowerCase().equals("teachers")) {
                teachersList.add(u);
            }
        }

        return teachersList;
    }

    
    /**
     * Returns a list of all admins in the repository. 
     * 
     * @return List<User> of all admins in the repository
     */
    public List<User> findAllAdmins() {
        LinkedList<User> adminsList = new LinkedList<>();

        List<User> userList = findAll();

        for(User u : userList) {
            if(u.getType().toLowerCase().equals("admins")) {
                adminsList.add(u);
            }
        }

        return adminsList;
    }

}
