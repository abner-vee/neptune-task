package com.neptunebank.order_service.payload.response;

public class OrderResponse {
    private String success;
    private String message;
    private Long id;
    private String name;
    private int quantity;
    private String status;

    // Constructors
    public OrderResponse() {}

    public OrderResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public OrderResponse(String success, String message, Long id, String name, int quantity, String status) {
        this.success = success;
        this.message = message;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters and Setters
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // === Manual Builder ===
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String success;
        private String message;
        private Long id;
        private String name;
        private int quantity;
        private String status;

        public Builder success(String success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(success, message, id, name, quantity, status);
        }
    }
}
