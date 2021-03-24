package com.tung.productservice.dto;

public class ProductDto {
    private int id;
    private String name;
    private Double price;
    private String category;

    public ProductDto() {
    }

    public ProductDto(String name, Double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public ProductDto(int id, String name, Double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
