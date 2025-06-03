package com.neptunebank.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "product_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, CONFIRMED, REJECTED
    }

    public Order() {
    }

    public Order(Long id, String product, int quantity, Status status) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String product;
        private int quantity;
        private Status status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder product(String product) {
            this.product = product;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder status(Order.Status status) {
            this.status = status;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setId(this.id);
            order.setProduct(this.product);
            order.setQuantity(this.quantity);
            order.setStatus(this.status);
            return order;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
