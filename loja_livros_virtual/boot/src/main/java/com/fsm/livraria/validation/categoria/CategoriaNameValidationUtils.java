package com.fsm.livraria.validation.categoria;

import com.fsm.livraria.repositories.CategoriaRepository;
import com.fsm.livraria.validation.autor.AutorNotDuplicateEmail;
import io.micronaut.core.annotation.Nullable;

import java.util.UUID;

/**
 * Utility methods to ease {@link AutorNotDuplicateEmail} validation.
 */
public final class CategoriaNameValidationUtils {

    private CategoriaNameValidationUtils() {
    }

    /**
     * @param email      the email to check
     * @param repository the repository to check against
     * @param uuid  the id of the current entity being validated (null for new entities)
     * @return Whether the email is unique in the database (excluding the current entity)
     */
    public static boolean isValid(@Nullable String email, CategoriaRepository repository, @Nullable UUID uuid) {
        if (email == null || email.isEmpty()) {
            return true; // Empty values will be caught by @NotBlank or other validators
        }

        if (uuid != null) {
            return !repository.existsByNomeAndUuidNot(email, uuid);
        }

        return !repository.existsByNome(email);
    }
}