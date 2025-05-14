package com.fsm.livraria.dto;

import com.fsm.livraria.validation.categoria.CategoriaNotDuplicateName;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Serdeable
@CategoriaNotDuplicateName(message = "Categoria já cadastrada")
public class CategoriaCreateRequest {

    private UUID uuid;

    @NotBlank
    private String name;

    public CategoriaCreateRequest() {
    }

    public CategoriaCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
