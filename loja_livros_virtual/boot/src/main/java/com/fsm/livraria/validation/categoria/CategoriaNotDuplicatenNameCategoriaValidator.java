package com.fsm.livraria.validation.categoria;

import com.fsm.livraria.dto.Categoria.CategoriaCreateRequest;
import com.fsm.livraria.repositories.CategoriaRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

import java.util.UUID;

@Factory
class CategoriaNotDuplicatenNameCategoriaValidator {

    private final CategoriaRepository repository;

    /**
     * Constructor for the NotDuplicateEmailAutorValidator.
     *
     * @param repository The repository for checking email existence
     */
    public CategoriaNotDuplicatenNameCategoriaValidator(CategoriaRepository repository) {
        this.repository = repository;
    }

    /**
     * @return A {@link ConstraintValidator} implementation of a {@link CategoriaNotDuplicateName} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<CategoriaNotDuplicateName, CategoriaCreateRequest> notDuplicateEmailValidator() {
        return (request, annotationMetadata, context) -> {
            UUID uuid =  request.getUuid();
            return CategoriaNameValidationUtils.isValid(request.getName(), repository, uuid);
        };
    }
}