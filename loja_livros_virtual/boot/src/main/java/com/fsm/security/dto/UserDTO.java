package com.fsm.security.dto;

import com.fsm.security.PasswordEncoder;
import com.fsm.security.entities.User;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

@Serdeable
@ReflectiveAccess
public record UserDTO(@NotBlank(message = "username é obrigatorio") String username, @NotBlank(message = "password é obrigatorio") String password,@NotEmpty(message = "roles são obrigatorias") Set<String> roles) {

    public User toEntity(PasswordEncoder passwordEncoder) {
        return new User(null, username, passwordEncoder.encode(password), true, false, false, false, List.of());
    }
}
