package com.fsm.livraria.validation.cupom;

import com.fsm.livraria.repositories.CupomRepository;

/**
 * Utility methods to ease {@link CupomNotDuplicateCodigo} validation.
 */
public final class CupomNotDuplicateCodigoValidationUtils {

    private CupomNotDuplicateCodigoValidationUtils() {
    }
    /**
     * @param code the code to check
     * @return Whether the code is unique in the database (excluding the current entity)
     */
    public static boolean isValid(String code, CupomRepository cupomRepository) {
        if (code == null || code.isEmpty()) {
            return true; // Valores vazios serão tratados por @NotBlank ou outros validadores
        }

        return !cupomRepository.existsByCodigo(code);
    }

}