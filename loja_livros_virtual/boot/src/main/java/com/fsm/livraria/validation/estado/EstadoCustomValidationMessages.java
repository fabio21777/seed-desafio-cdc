package com.fsm.livraria.validation.estado;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class EstadoCustomValidationMessages extends StaticMessageSource {

    public static final String NOT_DUPLICATE_NAME_MESSAGE = "O nome do estado já existe!";
    /**
     * The message suffix to use.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Default constructor to initialize messages.
     * via {@link #addMessage(String, String)}
     */
    public EstadoCustomValidationMessages() {
        addMessage(EstadoNotDuplicateName.class.getName() + MESSAGE_SUFFIX, NOT_DUPLICATE_NAME_MESSAGE);
    }
}