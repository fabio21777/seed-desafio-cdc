package com.fsm.livraria.dto;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class LivroList {

    private String title;

    private UUID uuid;

    public String getTitle() {
        return title;
    }

    public LivroList() {
    }

    public LivroList(String title, UUID uuid) {
        this.title = title;
        this.uuid = uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
