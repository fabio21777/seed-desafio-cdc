package com.fsm.livraria.validation.cpfcnpj;

import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
class CpfCnpjValidator {

    /**
     * @return A {@link ConstraintValidator} implementation of a {@link CPFOrCNPJ} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<CPFOrCNPJ, String> notDuplicateNameValidator() {
        return (cpfOrCnpj, annotationMetadata, context) -> {
            return CpfCnpjValidationUtils.isValid(cpfOrCnpj);
        };
    }
}