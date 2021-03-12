package com.tung.productservice.payload.response;

import lombok.Data;

@Data
public class ProductResponse {
    private int id;
    private String name;
    private Double price;
    private String category;

    public ProductResponse(int id, String name, Double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public ProductResponse() {
    }
}
