package br.com.modelo.backend.api.entities;

import br.com.modelo.backend.api.security.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_todo", schema = "public")
public class ToDo implements Serializable{


    private static final long serialVersionUID = 977351988912526040L;

    private Long id;
    private String toDo;
    private Boolean done;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private Usuario usuario;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ci_todo", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ds_todo", nullable = false)
    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }
    @Column(name = "fl_done", nullable = false)
    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_criacao", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_atualizacao", nullable = false)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PreUpdate
    public void preUpdate(){
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist(){
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
}
