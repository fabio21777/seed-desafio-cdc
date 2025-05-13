package com.fsm.livraria.dto;

import com.fsm.livraria.domain.Autor;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Serdeable
public class AutorDto {
    private String name;

    private String email;

    private String description;

    private LocalDateTime createdAt;

    public AutorDto() {
    }

    public  AutorDto(Autor autor){
        this.name = autor.getNome();
        this.email = autor.getEmail();
        this.description = autor.getDescricao();
        this.createdAt = autor.getCreatedAt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public  Autor toModel() {
        Autor autor = new Autor();
        autor.setNome(this.name);
        autor.setEmail(this.email);
        autor.setDescricao(this.description);
        return autor;
    }
}
