package com.tung.productservice.payload.response;

import lombok.Data;

@Data
public class ErrorDetails {
    private Boolean success;
    private String message;
    private String details;

    public ErrorDetails() {
    }

    public ErrorDetails(Boolean success, String message, String details) {
        this.success = success;
        this.message = message;
        this.details = details;
    }
}
