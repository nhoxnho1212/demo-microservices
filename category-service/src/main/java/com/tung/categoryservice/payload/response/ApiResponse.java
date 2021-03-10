package com.tung.categoryservice.payload.response;

public class ApiResponse {
    private Boolean success;
    private String message;

    // ---- Constructor ----

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // ---- Getter, Setter ----

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
}
