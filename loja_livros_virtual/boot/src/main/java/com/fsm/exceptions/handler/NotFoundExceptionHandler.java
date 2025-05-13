package com.fsm.exceptions.handler;

import com.fsm.exceptions.exception.NotFoundError;
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

import java.time.Instant;

@Singleton
@Requires(classes = {NotFoundError.class, ExceptionHandler.class})
@Produces
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundError, HttpResponse<StandardError>>{

    @Override
    public HttpResponse<StandardError> handle(HttpRequest request, NotFoundError exception) {
        ValidateError err = new ValidateError();
        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setStatus(status.getCode());
        err.setMessage(exception.getMessage());
        err.setTimestamp(Instant.now());
        err.setPath(request.getPath());
        err.setError("not found exception");
        return HttpResponse.status(status)
                .body(err);
    }
}
