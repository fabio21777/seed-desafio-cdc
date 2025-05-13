package com.fsm.security.entities;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;

@MappedEntity
public record Role(@Nullable
                   @Id
                   @GeneratedValue
                   Long id,
                   @NotBlank
                   String authority) {
}
