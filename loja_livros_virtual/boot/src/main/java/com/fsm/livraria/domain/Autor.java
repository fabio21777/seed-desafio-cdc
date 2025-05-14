package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

@MappedEntity
public class Autor extends BaseDomain {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    @Max(value = 400)
    private  String descricao;

    public Autor () {
    }

    /**
     * Construtor para criar um autor com email, nome e descrição.
     *
     * @param email     O email do autor.
     * @param nome      O nome do autor.
     * @param descricao A descrição do autor.
     */

    public Autor(String email, String nome, String descricao) {
        this.email = email;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
