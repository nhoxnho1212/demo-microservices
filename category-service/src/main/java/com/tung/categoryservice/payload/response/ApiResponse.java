package com.tung.categoryservice.payload.response;

public class ApiResponse {
    private Boolean success;
    private Object message;

    // ---- Constructor ----

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, Object message) {
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
