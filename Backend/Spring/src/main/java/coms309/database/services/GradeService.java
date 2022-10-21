package coms309.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import coms309.database.dataobjects.Grade;

@Service
public class GradeService implements RepositoryService<Grade> {

    private JpaRepository<Grade, Long> repository;

    public GradeService(JpaRepository<Grade, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Grade> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Grade> findAll() {
        return repository.findAll();
    }

    @Override
    public Grade create(Grade t) {
        return repository.save(t);
    }

    @Override
    public Grade update(Grade t) {
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
