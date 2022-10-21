package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.Assignment;

@Service
public class AssignmentService implements RepositoryService<Assignment> {

    private JpaRepository<Assignment, Long> repository;

    public AssignmentService(JpaRepository<Assignment, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Assignment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Assignment> findAll() {
        return repository.findAll();
    }

    @Override
    public Assignment create(Assignment t) {
        return repository.save(t);
    }

    @Override
    public Assignment update(Assignment t) {
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
