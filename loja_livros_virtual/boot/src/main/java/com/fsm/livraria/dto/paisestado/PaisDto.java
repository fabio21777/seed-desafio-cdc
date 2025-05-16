package com.fsm.livraria.dto.paisestado;

import com.fsm.livraria.domain.Pais;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;
@Serdeable
public class PaisDto {

    private UUID uuid;

    private String name;

    private String abreviation;

    public PaisDto() {
    }

    public PaisDto(Pais pais) {
        this.uuid = pais.getUuid();
        this.name = pais.getNome();
        this.abreviation = pais.getSigla();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
