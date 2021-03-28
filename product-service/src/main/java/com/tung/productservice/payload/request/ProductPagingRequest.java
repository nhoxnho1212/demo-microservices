package com.tung.productservice.payload.request;

import com.tung.productservice.dto.paging.Order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductPagingRequest {

    @NotNull
    private String name;

    @NotNull
    private List<Order> order;

    @NotNull
    @Min(0)
    private Integer start;

    @NotNull
    @Min(1)
    private Integer length;

    @NotNull
    private List<String> category;

    public ProductPagingRequest(String name, List<Order> orders, Integer start, Integer length, List<String> categories) {
        this.name = name;
        this.order = orders;
        this.start = start;
        this.length = length;
        this.category = categories;
    }

    public ProductPagingRequest() {
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
