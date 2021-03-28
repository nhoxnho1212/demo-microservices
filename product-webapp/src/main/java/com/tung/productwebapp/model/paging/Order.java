package com.tung.productwebapp.model.paging;

public class Order {
    private String columnName;
    private Direction dir;

    public Order(String column, Direction dir) {
        this.columnName = column;
        this.dir = dir;
    }

    public Order() {
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

}
