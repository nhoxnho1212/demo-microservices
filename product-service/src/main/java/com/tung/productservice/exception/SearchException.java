package com.tung.productservice.exception;

public class SearchException extends RuntimeException {

    private ServiceError errorCode;

    public SearchException(ServiceError errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceError getErrorCode() {
        return errorCode;
    }
}
