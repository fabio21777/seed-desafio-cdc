package com.fsm.livraria.dto;

import com.fsm.livraria.domain.Estado;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class EstadoDto {

    private UUID  uuid;

    private String name;

    private String abreviation;

    private PaisDto country;

    public EstadoDto() {
    }

    public  EstadoDto(Estado estado){
        this.uuid = estado.getUuid();
        this.name = estado.getNome();
        this.abreviation = estado.getSigla();
        this.country = new PaisDto(estado.getPais());
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

    public PaisDto getCountry() {
        return country;
    }

    public void setCountry(PaisDto country) {
        this.country = country;
    }
}
