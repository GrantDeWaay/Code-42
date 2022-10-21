package coms309.database.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.User;

@Service
public class UserService implements RepositoryService<User> {

    private JpaRepository<User, Long> repository;

    public UserService(JpaRepository<User, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    // public Optional<User> findByUsername(String username) {
    //     return repository.findByUsername(username);
    // }

    // public Optional<User> findByEmail(String email) {
    //     return repository.findByEmail(email);
    // }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    // these two methods do the same thing, but this might make usage more clear
    @Override
    public User create(User t) {
        return repository.save(t);
    }

    @Override
    public User update(User t) {
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
