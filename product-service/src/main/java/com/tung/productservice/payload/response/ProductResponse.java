package com.tung.productservice.payload.response;

import lombok.Data;

@Data
public class ProductResponse {
    private int id;
    private String name;
    private Double price;
    private String categoryId;

    public ProductResponse(int id, String name, Double price, String categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductResponse() {
    }
}
