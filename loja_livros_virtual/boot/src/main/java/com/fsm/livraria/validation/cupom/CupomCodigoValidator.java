package com.fsm.livraria.validation.cupom;

import com.fsm.livraria.repositories.CupomRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
class CupomCodigoValidator {

    private final CupomRepository repository;

    CupomCodigoValidator(CupomRepository repository) {
        this.repository = repository;
    }

    /**
     * @return A {@link ConstraintValidator} implementation of a {@link CupomNotDuplicateCodigo} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<CupomNotDuplicateCodigo, String> notDuplicateNameValidator() {
        return (codigo, annotationMetadata, context) -> {
            return CupomNotDuplicateCodigoValidationUtils.isValid(codigo, repository);
        };
    }
}