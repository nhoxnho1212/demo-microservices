package com.tung.productwebapp.model;

public class ProductRequest {
        private long id;
        private String name;
        private Double price;
        private String category;

        public ProductRequest(long id, String name, Double price, String category) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.category = category;
        }

        public ProductRequest() {
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
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
