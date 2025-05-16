package com.fsm.livraria.dto.Categoria;

import com.fsm.livraria.domain.Categoria;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;

@Serdeable
public class CategoriaDto {

    private String name;

    private LocalDateTime createdAt;

    public CategoriaDto() {
    }

    public CategoriaDto(String name) {
        this.name = name;
    }

    public CategoriaDto(Categoria categoria) {
        this.name = categoria.getNome();
        this.createdAt = categoria.getCreatedAt();
    }

    public CategoriaDto(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
