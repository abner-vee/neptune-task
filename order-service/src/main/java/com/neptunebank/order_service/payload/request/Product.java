package com.neptunebank.order_service.payload.request;

import lombok.Data;

public class Product {
    private String name;
    private Integer quantity;

    public Product() {
    }

    public Product(Integer quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



}
