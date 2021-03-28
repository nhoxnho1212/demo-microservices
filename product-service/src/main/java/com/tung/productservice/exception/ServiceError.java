package com.tung.productservice.exception;

public enum ServiceError {
    FILTER_MISSING_FIELD_ERROR(400),
    FILTER_INVALID_FIELD_ERROR(400),
    DATABASE_HAS_A_PROBLEM(500);
    private Integer statusCode;

    private ServiceError(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
