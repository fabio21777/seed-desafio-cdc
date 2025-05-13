package com.fsm.livraria.validation.autor;

import com.fsm.livraria.domain.Autor;
import com.fsm.livraria.repositories.AutorRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
class NotDuplicateEmailAutorValidator {

    private final AutorRepository autorRepository;

    /**
     * Constructor for the NotDuplicateEmailAutorValidator.
     *
     * @param autorRepository The repository for checking email existence
     */
    public NotDuplicateEmailAutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * @return A {@link ConstraintValidator} implementation of a {@link NotDuplicateEmail} constraint for type {@link String}.
     */
    @Singleton
    ConstraintValidator<NotDuplicateEmail, String> notDuplicateEmailValidator() {
        return (email, annotationMetadata, context) -> {
            // Check if it's an update operation
            Object validatedObject = context.getRootBean();
            Long id = null;
            if (validatedObject instanceof Autor) {
                id = ((Autor) validatedObject).getId();
            }

            return EmailValidationUtils.isValid(email, autorRepository, id);
        };
    }
}