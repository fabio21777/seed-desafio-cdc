package com.fsm.livraria.validation.categoria;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class CategoriaCustomValidationMessages extends StaticMessageSource {

    public static final String NOT_DUPLICATE_NAME_MESSAGE = "name already exists in the database";
    /**
     * The message suffix to use.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Default constructor to initialize messages.
     * via {@link #addMessage(String, String)}
     */
    public CategoriaCustomValidationMessages() {
        addMessage(CategoriaNotDuplicateName.class.getName() + MESSAGE_SUFFIX, NOT_DUPLICATE_NAME_MESSAGE);
    }
}