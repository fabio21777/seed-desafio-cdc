package com.fsm.livraria.validation.Livro;


import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.core.annotation.Nullable;

import java.util.UUID;

/**
 * Métodos utilitários para facilitar a validação de ISBN único.
 */
public final class LivroIsbnValidationUtils {

    private LivroIsbnValidationUtils() {
    }

    /**
     * @param isbn       o ISBN a ser verificado
     * @param repository o repositório para verificação
     * @param uuid       o id da entidade atual sendo validada (null para novas entidades)
     * @return Se o ISBN é único no banco de dados (excluindo a entidade atual)
     */
    public static boolean isValid(@Nullable String isbn, LivroRepository repository, @Nullable UUID uuid) {
        if (isbn == null || isbn.isEmpty()) {
            return true; // Valores vazios serão capturados por @NotBlank ou outros validadores
        }

        if (uuid != null) {
            return !repository.existsByIsbnAndUuidNot(isbn, uuid);
        }

        return !repository.existsByIsbn(isbn);
    }
}