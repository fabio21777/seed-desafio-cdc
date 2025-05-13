package com.fsm.exceptions.exception;

public class NotFoundError extends RuntimeException{

    private final String message;
    private final int status;

    public NotFoundError(String message, int status) {
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
