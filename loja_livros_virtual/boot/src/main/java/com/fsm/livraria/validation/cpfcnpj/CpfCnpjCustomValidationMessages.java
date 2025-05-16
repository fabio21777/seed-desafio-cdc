package com.fsm.livraria.validation.cpfcnpj;

import io.micronaut.context.StaticMessageSource;
import jakarta.inject.Singleton;

@Singleton
public class CpfCnpjCustomValidationMessages extends StaticMessageSource {

    /**
     * The message suffix to use.
     */
    private static final String MESSAGE_SUFFIX = ".message";

    /**
     * Default constructor to initialize messages.
     * via {@link #addMessage(String, String)}
     */
    public CpfCnpjCustomValidationMessages() {
        addMessage(CPFOrCNPJ.class.getName() + MESSAGE_SUFFIX, "CPF ou CNPJ inválido");
    }
}