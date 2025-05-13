package com.fsm.exceptions.exception;

public class AcessError extends RuntimeException{
    private final String message;
    private final int status;

    public AcessError(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
