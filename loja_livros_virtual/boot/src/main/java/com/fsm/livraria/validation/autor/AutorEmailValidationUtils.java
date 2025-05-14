package com.fsm.livraria.validation.autor;

import com.fsm.livraria.repositories.AutorRepository;
import io.micronaut.core.annotation.Nullable;

import java.util.UUID;

/**
 * Utility methods to ease {@link AutorNotDuplicateEmail} validation.
 */
public final class AutorEmailValidationUtils {

    private AutorEmailValidationUtils() {
    }

    /**
     * @param email the email to check
     * @param autorRepository the repository to check against
     * @param uuid the id of the current entity being validated (null for new entities)
     * @return Whether the email is unique in the database (excluding the current entity)
     */
    public static boolean isValid(@Nullable String email, AutorRepository autorRepository, @Nullable UUID uuid) {
        if (email == null || email.isEmpty()) {
            return true; // Empty values will be caught by @NotBlank or other validators
        }

        if (uuid != null) {
            return !autorRepository.existsByEmailAndUuidNot(email, uuid);
        }
        return !autorRepository.existsByEmail(email);
    }
}