package com.fsm.livraria.dto;

import com.fsm.livraria.domain.Estado;
import com.fsm.livraria.repositories.AutorRepository;
import com.fsm.livraria.repositories.PaisRepository;
import com.fsm.livraria.validation.estado.EstadoNotDuplicateName;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Serdeable
public class EstadoCreateRequest {

    @NotBlank(message = "Nome não pode ser vazio")
    @EstadoNotDuplicateName(message = "Já existe um estado com esse nome")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;

    @Size(max = 2, message = "A sigla deve ter no máximo 2 caracteres")
    @NotBlank(message = "A Sigla não pode ser vazio")
    private String abreviation;

    @NotNull(message = "País não pode ser vazio")
    private UUID country;

    public EstadoCreateRequest(String name, String abreviation, UUID country) {
        this.name = name;
        this.abreviation = abreviation;
        this.country = country;
    }

    public Estado toEntity(PaisRepository autorRepository) {
       return new Estado(this.name, this.abreviation, autorRepository.findByUuidOrElseThrow(country));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }
}
