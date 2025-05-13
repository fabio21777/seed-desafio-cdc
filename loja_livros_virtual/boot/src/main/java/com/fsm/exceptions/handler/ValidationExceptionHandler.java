package com.fsm.exceptions.handler;

import com.fsm.exceptions.exception.ValidateError;
import io.micronaut.context.annotation.Primary;
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
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
@Produces
@Primary
public class ValidationExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<ValidateError>>{

    @Override
    public HttpResponse<ValidateError> handle(HttpRequest request, ConstraintViolationException exception) {
        ValidateError err = new ValidateError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        err.setStatus(status.getCode());
        err.setMessage(exception.getMessage());
        err.setTimestamp(Instant.now());
        err.setPath(request.getPath());
        err.setError("validate exception");
        exception.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.contains(".") ?
                    propertyPath.substring(propertyPath.lastIndexOf('.') + 1) :
                    propertyPath;

            err.addFieldMessage(fieldName, violation.getMessage());
        });

        return HttpResponse.status(status)
                .body(err);
    }
}
