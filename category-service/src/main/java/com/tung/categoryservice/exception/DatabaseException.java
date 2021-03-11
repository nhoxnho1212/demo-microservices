package com.tung.categoryservice.exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends RuntimeException{
    HttpStatus httpStatus;

    public DatabaseException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public DatabaseException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
