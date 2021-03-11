package com.tung.categoryservice.config.constant;

public enum ErrorMessages {
    CATEGORY_NOT_FOUND("Category not found"),
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
