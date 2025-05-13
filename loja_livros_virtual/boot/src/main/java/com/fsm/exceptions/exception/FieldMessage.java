package com.fsm.exceptions.exception;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class FieldMessage {


    private String field;
    private String message;

    public FieldMessage() { }

    public FieldMessage(String field, String message) {
        super();
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
