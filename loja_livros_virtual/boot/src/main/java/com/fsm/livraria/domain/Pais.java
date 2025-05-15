package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;

@MappedEntity
public class Pais extends BaseDomain {

    @NotBlank
    private String nome;

    @NotBlank
    private String sigla;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
