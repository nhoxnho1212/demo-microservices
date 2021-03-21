package com.tung.productwebapp.payload.request;

public class ApiRequest {
    private Boolean success;
    private Object message;

    // ---- Constructor ----

    public ApiRequest() {
    }

    public ApiRequest(Boolean success, Object message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
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

