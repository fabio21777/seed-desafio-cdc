package com.fsm.livraria.validation.pais;

import com.fsm.livraria.repositories.PaisRepository;

/**
 * Utility methods to ease {@link PaisNotDuplicateName} validation.
 */
public final class PaisValidationUtils {

    private PaisValidationUtils() {
    }

    /**
     * @param name the name to check
     * @param repository the repository to check against
     * @return Whether the name is unique in the database (excluding the current entity)
     */
    public static boolean isValid(String name, PaisRepository repository) {
        if (name == null || name.isEmpty()) {
            return true; // Empty values will be caught by @NotBlank or other validators
        }

        return !repository.existsByNome(name);
    }
}