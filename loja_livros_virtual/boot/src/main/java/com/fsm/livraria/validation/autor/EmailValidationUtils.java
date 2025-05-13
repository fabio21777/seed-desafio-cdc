package com.fsm.livraria.validation.autor;

import com.fsm.livraria.repositories.AutorRepository;
import io.micronaut.core.annotation.Nullable;

/**
 * Utility methods to ease {@link NotDuplicateEmail} validation.
 */
public final class EmailValidationUtils {

    private EmailValidationUtils() {
    }

    /**
     * @param email the email to check
     * @param autorRepository the repository to check against
     * @param currentId the id of the current entity being validated (null for new entities)
     * @return Whether the email is unique in the database (excluding the current entity)
     */
    public static boolean isValid(@Nullable String email, AutorRepository autorRepository, @Nullable Long currentId) {
        if (email == null || email.isEmpty()) {
            return true; // Empty values will be caught by @NotBlank or other validators
        }

        return !autorRepository.existsByEmailAndIdNot(email, currentId != null ? currentId : -1L);
    }
}