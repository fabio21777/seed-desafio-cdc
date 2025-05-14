package com.fsm.livraria.validation.livro;

import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.core.annotation.Nullable;

import java.util.UUID;

/**
 * Métodos utilitários para facilitar a validação de título único.
 */
public final class LivroTitleValidationUtils {

    private LivroTitleValidationUtils() {
    }

    /**
     * @param title      o título a ser verificado
     * @param repository o repositório para verificação
     * @param uuid       o id da entidade atual sendo validada (null para novas entidades)
     * @return Se o título é único no banco de dados (excluindo a entidade atual)
     */
    public static boolean isValid(@Nullable String title, LivroRepository repository, @Nullable UUID uuid) {
        if (title == null || title.isEmpty()) {
            return true; // Valores vazios serão capturados por @NotBlank ou outros validadores
        }

        if (uuid != null) {
            return !repository.existsByTituloAndUuidNot(title, uuid);
        }

        return !repository.existsByTitulo(title);
    }
}