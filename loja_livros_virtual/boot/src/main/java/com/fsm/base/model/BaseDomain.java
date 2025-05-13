package com.fsm.base.model;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.event.PrePersist;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseDomain {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private long id;

    // @AutoPopulated gerará automaticamente um UUID ao persistir
    @AutoPopulated(updatable = false)
    private UUID uuid;

    // @DateCreated cuidará automaticamente da data de criação
    @DateCreated
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == this) ||
                (obj instanceof BaseDomain && obj.getClass().equals(getClass()) && getId() == ((BaseDomain) obj).getId());
    }

    @Override
    public String toString() {
        return getClass().getName() + ":" + getId();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}