package com.fsm.livraria.validation.estado;

import com.fsm.livraria.repositories.EstadoRepository;
import com.fsm.livraria.repositories.PaisRepository;
import io.micronaut.core.annotation.Nullable;

import java.util.UUID;

/**
 * Utility methods to ease {@link EstadoNotDuplicateName} validation.
 */
public final class EstadoValidationUtils {

    private EstadoValidationUtils() {
    }

    /**
     * @param name the name to check
     * @param repository the repository to check against
     * @return Whether the name is unique in the database (excluding the current entity)
     */
    public static boolean isValid(String name, EstadoRepository repository) {
        if (name == null || name.isEmpty()) {
            return true; // Empty values will be caught by @NotBlank or other validators
        }

        return !repository.existsByNome(name);
    }
}