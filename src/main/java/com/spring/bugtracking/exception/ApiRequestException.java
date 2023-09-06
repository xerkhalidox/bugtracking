package com.spring.bugtracking.exception;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {
    private final HttpStatus status;

    public ApiRequestException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getHttpStatus() {
        return status;
    }
}
