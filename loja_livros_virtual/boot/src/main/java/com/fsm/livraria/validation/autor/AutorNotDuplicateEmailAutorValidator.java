package com.fsm.livraria.validation.autor;

import com.fsm.exceptions.exception.ServiceError;
import com.fsm.livraria.domain.Autor;
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
    ConstraintValidator<AutorNotDuplicateEmail, String> notDuplicateEmailValidator() {
        return (email, annotationMetadata, context) -> {
            // Check if it's an update operation
            Object validatedObject = context.getRootBean();
            UUID uuid = null;
            if (validatedObject instanceof AutorCreateRequest) {
                uuid = ((AutorCreateRequest) validatedObject).uuid();
            }
            return AutorEmailValidationUtils.isValid(email, autorRepository, uuid);
        };
    }
}