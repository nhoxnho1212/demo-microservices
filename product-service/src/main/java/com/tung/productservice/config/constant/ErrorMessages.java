package com.tung.productservice.config.constant;

public enum ErrorMessages {
    DATABASE_HAS_A_PROBLEM("The database has encountered a problem.");
    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
