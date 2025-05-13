package com.fsm.exceptions.exception;

public class ServiceError extends  RuntimeException {

    private final String message;
    private final int status;

    public ServiceError(String message, int status) {
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
