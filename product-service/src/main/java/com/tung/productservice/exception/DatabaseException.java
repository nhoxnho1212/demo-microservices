package com.tung.productservice.exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends RuntimeException {

    private ServiceError errorCode;

    public DatabaseException(ServiceError errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceError getErrorCode() {
        return errorCode;
    }
}
