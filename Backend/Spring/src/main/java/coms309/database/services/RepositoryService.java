package coms309.database.services;

import java.util.List;
import java.util.Optional;

public interface RepositoryService<T> {
    
    Optional<T> findById(Long id);

    List<T> findAll();

    T create(T t);

    T update(T t);

    void delete(Long id);

    boolean contains(Long id);

    long count();

}
