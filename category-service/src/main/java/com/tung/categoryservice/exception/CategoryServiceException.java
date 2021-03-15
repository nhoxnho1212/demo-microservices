package com.tung.categoryservice.exception;

import org.springframework.http.HttpStatus;

public class CategoryServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public CategoryServiceException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public CategoryServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
