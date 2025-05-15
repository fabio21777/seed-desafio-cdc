package com.fsm.livraria.validation.estado;

import com.fsm.livraria.dto.AutorCreateRequest;
import com.fsm.livraria.repositories.EstadoRepository;
import com.fsm.livraria.repositories.PaisRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

import java.util.UUID;

@Factory
class EstadoNotDuplicateNameValidator {

    private final EstadoRepository repository;


    /**
     * Constructor for the NotDuplicateEmailAutorValidator.
     *
     * @param repository The repository for checking email existence
     */

    EstadoNotDuplicateNameValidator(EstadoRepository repository) {
        this.repository = repository;
    }


    /**
     * @return A {@link ConstraintValidator} implementation of a {@link EstadoNotDuplicateName} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<EstadoNotDuplicateName, String> notDuplicateNameValidator() {
        return (name, annotationMetadata, context) -> {
            return EstadoValidationUtils.isValid(name, repository);
        };
    }
}