package com.fsm.exceptions.handler;

import com.fsm.exceptions.exception.ServiceError;
import com.fsm.exceptions.exception.StandardError;
import com.fsm.exceptions.exception.ValidateError;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;

import java.time.Instant;

@Singleton
@Requires(classes = {ServiceError.class, ExceptionHandler.class})
@Produces
public class ServiceExceptionHandler implements ExceptionHandler<ServiceError, HttpResponse<StandardError>>{

    @Override
    public HttpResponse<StandardError> handle(HttpRequest request, ServiceError exception) {
        ValidateError err = new ValidateError();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        err.setStatus(status.getCode());
        err.setMessage(exception.getMessage());
        err.setTimestamp(Instant.now());
        err.setPath(request.getPath());
        err.setError("service exception");
        return HttpResponse.status(status)
                .body(err);
    }
}
