package br.com.modelo.backend.api.services.impl;

import br.com.modelo.backend.api.entities.ToDo;
import br.com.modelo.backend.api.repositories.ToDoRepository;
import br.com.modelo.backend.api.services.ToDoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoServices{
    private static final Logger log = LoggerFactory.getLogger(ToDoServiceImpl.class);

    @Autowired
    private ToDoRepository toDoRepository;

    @Override
    public Page<ToDo> buscarPorToDo(String buscar, PageRequest pageRequest) {
        log.info("Bucando ToDo por palavra ilike", buscar);
        return this.toDoRepository.findByToDoContainingIgnoreCase(buscar,pageRequest);
    }

    @Override
    public Page<ToDo> listarPorToDo(PageRequest pageRequest) {
        return this.toDoRepository.findAll(pageRequest);
    }

    @Override
    public Optional<ToDo> buscarPorId(Long id) {
        log.info("Buscando ToDo por ID: {}", id);
        return this.toDoRepository.findById(id);
    }

    @Override
    public ToDo persistir(ToDo toDo) {
        log.info("Persistindo ToDo: {}", toDo);
        return this.toDoRepository.save(toDo);
    }

    @Override
    public void remover(Long id) {
        log.info("Removendo ToDo id: {}", id);
        this.toDoRepository.deleteById(id);
    }


}
