package com.fsm.livraria.dto;

import com.fsm.livraria.validation.NotDuplicateEmail;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Serdeable
@ReflectiveAccess
public record AutorCreateRequest(
        @NotBlank(message = "Nome não pode ser vazio")
        String name,
        @NotBlank(message = "Email não pode ser vazio")
        @Email(message = "Email inválido")
        @NotDuplicateEmail(message = "Email já cadastrado")
        String email,
        @Size(max = 400, message = "Descrição não pode ter mais de 400 caracteres")
        String description
) {
}
