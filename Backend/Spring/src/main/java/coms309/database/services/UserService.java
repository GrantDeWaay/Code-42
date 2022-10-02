package coms309.database.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import coms309.database.dataobjects.User;
import coms309.database.repository.UserRepository;

public class UserService {
    
    @Autowired
    private UserRepository repository;

    public UserService() {

    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    // these two methods do the same thing, but this might make usage more clear
    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public boolean containsUser(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

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
