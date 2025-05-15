package com.fsm.livraria.validation.autor;

import com.fsm.livraria.dto.AutorCreateRequest;
import com.fsm.livraria.repositories.AutorRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

import java.util.UUID;

@Factory
class AutorNotDuplicateEmailAutorValidator {

    private final AutorRepository autorRepository;

    /**
     * Constructor for the NotDuplicateEmailAutorValidator.
     *
     * @param autorRepository The repository for checking email existence
     */
    public AutorNotDuplicateEmailAutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * @return A {@link ConstraintValidator} implementation of a {@link AutorNotDuplicateEmail} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<AutorNotDuplicateEmail, AutorCreateRequest> notDuplicateEmailValidator() {
        return (request, annotationMetadata, context) -> {
            UUID uuid =   request.uuid();
            return AutorEmailValidationUtils.isValid(request.email(), autorRepository, uuid);
        };
    }
}