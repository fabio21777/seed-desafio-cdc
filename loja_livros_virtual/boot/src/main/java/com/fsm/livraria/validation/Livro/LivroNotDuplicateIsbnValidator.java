package com.fsm.livraria.validation.Livro;

import com.fsm.livraria.dto.LivroCreateRequestDto;
import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

import java.util.UUID;

@Factory
class LivroNotDuplicateIsbnValidator {

    private final LivroRepository repository;

    /**
     * Construtor para o LivroNotDuplicateIsbnValidator.
     *
     * @param repository O repositório para verificar a existência do ISBN
     */
    public LivroNotDuplicateIsbnValidator(LivroRepository repository) {
        this.repository = repository;
    }

    /**
     * @return Uma implementação {@link ConstraintValidator} de uma restrição {@link LivroNotDuplicateIsbn} para o tipo {@link String}.
     */
    @Singleton
    ConstraintValidator<LivroNotDuplicateIsbn, Object> notDuplicateIsbnValidator() {
        return (value, annotationMetadata, context) -> {
            if (value == null) {
                return true;
            }

            if (value instanceof LivroCreateRequestDto) {
                LivroCreateRequestDto dto = (LivroCreateRequestDto) value;
                return LivroIsbnValidationUtils.isValid(dto.getIsbn(), repository, dto.getUuid());
            }

            return true;
        };
    }
}