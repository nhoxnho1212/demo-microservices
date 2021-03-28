package com.tung.productwebapp.model.paging;

import java.util.List;

public class Page<T> {
    private List<T> data;
    private Integer total;

    public Page(List<T> data) {
        this.data = data;
        this.total = data.size();
    }

    public Page(List<T> data, Integer total) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
