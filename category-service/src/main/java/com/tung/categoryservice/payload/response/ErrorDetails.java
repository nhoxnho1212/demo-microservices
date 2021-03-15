package com.tung.categoryservice.payload.response;

public class ErrorDetails {
    private Boolean success;
    private String message;
    private String details;

    public ErrorDetails() { }

    public ErrorDetails(Boolean success, String message, String details) {
        this.success = success;
        this.message = message;
        this.details = details;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
