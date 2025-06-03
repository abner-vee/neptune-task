package com.neptunebank.order_service.payload.request;

public class OrderRequest {
    private String name;
    private Integer quantity;

    public OrderRequest() {
    }

    public OrderRequest(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
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
