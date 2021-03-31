package com.tung.productwebapp.model;

import com.tung.productwebapp.model.paging.Order;

import java.util.List;

public class SearchAndPagingProductRequest {
    private String name;
    private List<Order> order;
    private Integer start;
    private Integer length;

    private List<String> category;

    public SearchAndPagingProductRequest(String name, List<Order> orders, Integer start, Integer length, List<String> categories) {
        this.name = name;
        this.order = orders;
        this.start = start;
        this.length = length;
        this.category = categories;
    }

    public SearchAndPagingProductRequest() {
    }

    @Override
    public String toString() {
        return "SearchAndPagingProductRequest{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", start=" + start +
                ", length=" + length +
                ", category=" + category +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
