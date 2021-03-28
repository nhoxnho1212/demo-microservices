package com.tung.productservice.payload.response;

import lombok.Data;

@Data
public class ErrorDetails {
    private Boolean success;
    private String message;
    private Object details;

    public ErrorDetails() {
    }

    public ErrorDetails(Boolean success, String message, Object details) {
        this.success = success;
        this.message = message;
        this.details = details;
    }
}
