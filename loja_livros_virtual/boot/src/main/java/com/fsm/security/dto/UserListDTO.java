package com.fsm.security.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Set;

@Serdeable
public record UserListDTO (String username, Set<String> roles ) {
}
