package com.fsm.livraria.validation.categoria;

import com.fsm.exceptions.exception.ServiceError;
import com.fsm.livraria.domain.Autor;
import com.fsm.livraria.domain.Categoria;
import com.fsm.livraria.dto.CategoriaCreateRequest;
import com.fsm.livraria.repositories.AutorRepository;
import com.fsm.livraria.repositories.CategoriaRepository;
import com.fsm.livraria.validation.autor.AutorEmailValidationUtils;
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
    ConstraintValidator<CategoriaNotDuplicateName, String> notDuplicateEmailValidator() {
        return (name, annotationMetadata, context) -> {
            // Check if it's an update operation
            Object validatedObject = context.getRootBean();
            UUID uuid = null;
            if (validatedObject instanceof CategoriaCreateRequest) {
                uuid = ((CategoriaCreateRequest) validatedObject).getUuid();
            }
            return CategoriaNameValidationUtils.isValid(name, repository, uuid);
        };
    }
}