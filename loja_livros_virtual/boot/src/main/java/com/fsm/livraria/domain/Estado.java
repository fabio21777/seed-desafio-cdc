package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@MappedEntity
public class Estado extends BaseDomain {
    @NotBlank
    private String nome;

    @NotBlank
    private String sigla;

    @NotNull
    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Pais pais;

    public Estado() {
    }

    public Estado(String name, String abreviation, Pais pais) {
        this.nome = name;
        this.sigla = abreviation;
        this.pais = pais;
    }



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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
