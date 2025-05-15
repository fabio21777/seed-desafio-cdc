package com.fsm.livraria.validation.pais;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class PaisCustomValidationMessages extends StaticMessageSource {

    public static final String NOT_DUPLICATE_NAME_MESSAGE = "O nome do pais já existe!";
    /**
     * The message suffix to use.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Default constructor to initialize messages.
     * via {@link #addMessage(String, String)}
     */
    public PaisCustomValidationMessages() {
        addMessage(PaisNotDuplicateName.class.getName() + MESSAGE_SUFFIX, NOT_DUPLICATE_NAME_MESSAGE);
    }
}