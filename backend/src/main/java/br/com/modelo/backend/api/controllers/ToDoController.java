package br.com.modelo.backend.api.controllers;

import br.com.modelo.backend.api.dtos.ToDoDto;
import br.com.modelo.backend.api.entities.ToDo;
import br.com.modelo.backend.api.response.Response;
import br.com.modelo.backend.api.security.entities.Usuario;
import br.com.modelo.backend.api.security.services.UsuarioService;
import br.com.modelo.backend.api.security.utils.JwtTokenUtil;
import br.com.modelo.backend.api.services.ToDoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;


@RestController
@RequestMapping("api/todo")
@CrossOrigin(origins = "*")
public class ToDoController {

    private static Logger log = LoggerFactory.getLogger(ToDoController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ToDoServices toDoServices;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

   @GetMapping(value = "listar")
    public ResponseEntity<Response<Page<ToDoDto>>> listarToDo(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir
    ){
        log.info("Listando tarefas all");
        Response<Page<ToDoDto>> response = new Response<Page<ToDoDto>>();

        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<ToDo> todos = this.toDoServices.listarPorToDo(pageRequest);
        Page<ToDoDto> toDoDtos = todos.map(toDo -> this.converteToDoDto(toDo));

        response.setData(toDoDtos);
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "listar")
    public ResponseEntity<Response<Page<ToDoDto>>> listarToDoSearch(
            @RequestBody ToDoDto toDoDto,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir
    ){
        log.info("Listando tarefas (List)");
        Response<Page<ToDoDto>> response = new Response<Page<ToDoDto>>();

        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<ToDo> todos = this.toDoServices.buscarPorToDo(toDoDto.getToDo(),pageRequest);
        Page<ToDoDto> toDoDtos = todos.map(toDo -> this.converteToDoDto(toDo));

        response.setData(toDoDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<ToDoDto>> getToDoById(@PathVariable("id") Long id){
        log.info("Pesquisando por id");

        Response<ToDoDto> response = new Response<ToDoDto>();
        Optional<ToDo> toDo = this.toDoServices.buscarPorId(id);

        if(!toDo.isPresent()){
            log.info("ToDo não encontrado para o id {}", id);
            response.getErrors().add("ToDo não encontrado para o id  " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converteToDoDto(toDo.get()));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Response<ToDoDto>> atualizar(
            @PathVariable("id") Long id, @Valid @RequestBody ToDoDto toDoDto, BindingResult result) throws ParseException
    {
        log.info("Atualizando toDo: {}", toDoDto.toString());
        Response<ToDoDto> response = new Response<ToDoDto>();
        toDoDto.setId(Optional.of(id));
        //validarUsuario(toDoDto, result);
        ToDo toDo = this.converteToDoDtoParaToDo(toDoDto, result);

        if(result.hasErrors()){
            log.error("Erro validando ToDo: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        toDo = this.toDoServices.persistir(toDo);
        response.setData(this.converteToDoDto(toDo));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<ToDoDto>> adicionar(@Valid @RequestBody ToDoDto toDoDto, BindingResult result) throws ParseException{

        log.info("Adcionando lançamento: {}", toDoDto.toString());
        Response<ToDoDto> response = new Response<ToDoDto>();
        validarUsuario(toDoDto, result);
        ToDo toDo = this.converteToDoDtoParaToDo(toDoDto, result);

        if(result.hasErrors()){
            log.error("Erro validando ToDo: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        toDo = this.toDoServices.persistir(toDo);
        response.setData(this.converteToDoDto(toDo));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
        log.info("Removendo toDo: {}", id);
        Response<String> response = new Response<String>();
        Optional<ToDo> toDo = this.toDoServices.buscarPorId(id);

        if(!toDo.isPresent()){
            log.info("Erro ao remover devido ao toDo ID: {} ser inválido.", id);
            response.getErrors().add("Erro ao remover toDo. Registro não encontrado para o id "+id);
            return ResponseEntity.badRequest().body(response);
        }

        this.toDoServices.remover(id);
        Response<String> message = new Response<String>();
        message.setData("Removido todo ID: "+id);
        return ResponseEntity.ok(message);
    }

    private ToDo converteToDoDtoParaToDo(ToDoDto toDoDto, BindingResult result) throws ParseException{
        ToDo toDo = new ToDo();
        if(toDoDto.getId().isPresent()){
            Optional<ToDo> td = this.toDoServices.buscarPorId(toDoDto.getId().get());
            if(td.isPresent()){
                toDo = td.get();
            }else{
                result.addError(new ObjectError("ToDo", "ToDo não encvontrado."));
            }
        }else{
            toDo.setUsuario(new Usuario());
            toDo.getUsuario().setId(toDoDto.getUsuarioId());
        }

        toDo.setToDo(toDoDto.getToDo());
        toDo.setDone(toDoDto.getDone());

        return toDo;
    }

    private void validarUsuario(ToDoDto toDoDto, BindingResult result) {
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(toDoDto.getAccess());
        Optional<Usuario> user = this.usuarioService.buscarPorEmail(usernameFromToken);
        toDoDto.setUsuarioId(user.get().getId());


        if (toDoDto.getUsuarioId() == null){
            result.addError(new ObjectError("Usuário", "Usuário não informado."));
            return;
        }

        log.info("Validando usuario id {}", toDoDto.getUsuarioId());
        if(!user.isPresent()){
            result.addError(new ObjectError("Usuário", "Usuário não encontrado. ID inexistente."));
        }
    }

    private ToDoDto converteToDoDto(ToDo toDo){
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(Optional.of(toDo.getId()));
        toDoDto.setToDo(toDo.getToDo());
        toDoDto.setDone(toDo.getDone());
        toDoDto.setUsuarioId(toDo.getUsuario().getId());
        toDoDto.setDataCriacao(toDo.getDataCriacao().toString());

        String dtAtualizacao = null;
        try {
            dtAtualizacao = toDo.getDataAtualizacao().toString();
        }catch (Exception e){
            log.warn("Data de Atualizacao vazia. {}", e.getMessage());
        }
        toDoDto.setDataAtualizacao(dtAtualizacao);

        return toDoDto;
    }
}
