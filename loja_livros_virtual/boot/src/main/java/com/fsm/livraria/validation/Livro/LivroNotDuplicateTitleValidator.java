package com.fsm.livraria.validation.Livro;

import com.fsm.livraria.dto.LivroCreateRequestDto;
import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

import java.util.UUID;

@Factory
class LivroNotDuplicateTitleValidator {

    private final LivroRepository repository;

    /**
     * Construtor para o LivroNotDuplicateTitleValidator.
     *
     * @param repository O repositório para verificar a existência do título
     */
    public LivroNotDuplicateTitleValidator(LivroRepository repository) {
        this.repository = repository;
    }

    /**
     * @return Uma implementação {@link ConstraintValidator} de uma restrição {@link LivroNotDuplicateTitle} para o tipo {@link String}.
     */
    @Singleton
    ConstraintValidator<LivroNotDuplicateTitle, Object> notDuplicateTitleValidator() {
        return (value, annotationMetadata, context) -> {
            if (value == null) {
                return true;
            }

            if (value instanceof LivroCreateRequestDto dto) {
                return LivroTitleValidationUtils.isValid(dto.getTitle(), repository, dto.getUuid());
            }

            return true;
        };
    }
}
