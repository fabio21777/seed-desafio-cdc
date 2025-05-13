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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@Singleton
@Requires(classes = {Throwable.class, ExceptionHandler.class})
@Produces
public class InternalExcptionHandler implements ExceptionHandler<Throwable, HttpResponse<StandardError>> {

    private static final Logger logger = LoggerFactory.getLogger(InternalExcptionHandler.class);

    @Override
    public HttpResponse<StandardError> handle(HttpRequest request, Throwable exception) {
        ValidateError err = new ValidateError();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        err.setStatus(status.getCode());
        err.setTimestamp(Instant.now());
        err.setPath(request.getPath());
        err.setError("internal server error");
        logger.error("error não mapeado", exception);
        return HttpResponse.status(status)
                .body(err);
    }


}
