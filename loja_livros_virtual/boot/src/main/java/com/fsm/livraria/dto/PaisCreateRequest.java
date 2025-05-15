package com.fsm.livraria.dto;

import com.fsm.livraria.validation.pais.PaisNotDuplicateName;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Serdeable
public class PaisCreateRequest {

    private UUID uuid;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @PaisNotDuplicateName(message = "Já existe um país com esse nome")
    private String name;

    @NotBlank(message = "Sigla não pode ser vazio")
    @Size(max = 2, message = "Sigla deve ter no máximo 2 caracteres")
    private String abbreviation;

    public PaisCreateRequest(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
