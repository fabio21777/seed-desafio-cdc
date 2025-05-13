package com.fsm.livraria.validation;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class CustomValidationMessages extends StaticMessageSource {

    public static final String NOT_DUPLICATE_EMAIL_MESSAGE = "email already exists in the database";
    /**
     * The message suffix to use.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Default constructor to initialize messages.
     * via {@link #addMessage(String, String)}
     */
    public CustomValidationMessages() {
        addMessage(NotDuplicateEmail.class.getName() + MESSAGE_SUFFIX, NOT_DUPLICATE_EMAIL_MESSAGE);
    }
}