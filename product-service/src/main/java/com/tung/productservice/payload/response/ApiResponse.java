package com.tung.productservice.payload.response;

import lombok.Data;

@Data
public class ApiResponse {
    private Boolean success;
    private Object message;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, Object message) {
        this.success = success;
        this.message = message;
    }
}
