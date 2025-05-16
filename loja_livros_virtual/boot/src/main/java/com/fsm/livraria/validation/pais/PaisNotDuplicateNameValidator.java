package com.fsm.livraria.validation.pais;

import com.fsm.livraria.repositories.PaisRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
class PaisNotDuplicateNameValidator {

    private final PaisRepository repository;


    /**
     * Constructor for the NotDuplicateEmailAutorValidator.
     *
     * @param repository The repository for checking email existence
     */

    PaisNotDuplicateNameValidator(PaisRepository repository) {
        this.repository = repository;
    }


    /**
     * @return A {@link ConstraintValidator} implementation of a {@link PaisNotDuplicateName} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<PaisNotDuplicateName, String> notDuplicateNameValidator() {
        return (nome, annotationMetadata, context) -> {
            return PaisValidationUtils.isValid(nome, repository);
        };
    }
}