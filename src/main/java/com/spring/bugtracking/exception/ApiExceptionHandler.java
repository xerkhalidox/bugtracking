package com.spring.bugtracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleException(ApiRequestException e) {
        ApiException apiException = new ApiException(
                e.getLocalizedMessage(),
                e,
                e.getHttpStatus(),
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(apiException, e.getHttpStatus());
    }
}
