package com.fsm.livraria.validation.livro;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class LivroCustomValidationMessages extends StaticMessageSource {

    public static final String NOT_DUPLICATE_TITLE_MESSAGE = "já existe um livro com este título";
    public static final String NOT_DUPLICATE_ISBN_MESSAGE = "já existe um livro com este ISBN";

    /**
     * O sufixo de mensagem a ser usado.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Construtor padrão para inicializar mensagens
     * via {@link #addMessage(String, String)}
     */
    public LivroCustomValidationMessages() {
        addMessage(LivroNotDuplicateTitle.class.getName() + MESSAGE_SUFFIX, NOT_DUPLICATE_TITLE_MESSAGE);
        addMessage(LivroNotDuplicateIsbn.class.getName() + MESSAGE_SUFFIX, NOT_DUPLICATE_ISBN_MESSAGE);
    }
}
