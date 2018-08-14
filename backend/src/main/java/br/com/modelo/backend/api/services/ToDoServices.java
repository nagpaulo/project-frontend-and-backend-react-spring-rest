package br.com.modelo.backend.api.services;

import br.com.modelo.backend.api.entities.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ToDoServices {
    Page<ToDo> buscarPorToDo(String buscar, PageRequest pageRequest);
    Page<ToDo> listarPorToDo(PageRequest pageRequest);
    Optional<ToDo> buscarPorId(Long id);
    ToDo persistir(ToDo toDo);

    /**
     * Remove um ToDo da base de dados.
     *
     * @param id
     */
    void remover(Long id);
}
