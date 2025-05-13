package com.fsm.exceptions.exception;

import io.micronaut.serde.annotation.Serdeable;

import java.util.ArrayList;
import java.util.List;

@Serdeable
public class ValidateError extends StandardError{

    private final List<FieldMessage> erros = new ArrayList<>();

    public List<FieldMessage> getErros() {
        return erros;
    }

    public void addFieldMessage(String field, String message) {
        erros.add(new FieldMessage(field,message));
    }

}
