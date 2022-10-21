package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.Course;

@Service
public class CourseService implements RepositoryService<Course> {

    private JpaRepository<Course, Long> repository;

    public CourseService(JpaRepository<Course, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public Course create(Course t) {
        return repository.save(t);
    }

    @Override
    public Course update(Course t) {
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
