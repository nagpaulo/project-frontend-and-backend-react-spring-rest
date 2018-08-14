package br.com.modelo.backend.api.repositories;

import br.com.modelo.backend.api.entities.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ToDoRepository extends CrudRepository<ToDo, Long>{

    Page<ToDo> findByToDoContainingIgnoreCase(@Param("toDo") String toDo, Pageable pageable);
    Optional<ToDo> findById(@Param("id") Long id);

    Page<ToDo> findByToDo(Pageable pageable);

    Page<ToDo> findAll(Pageable pageable);

}
