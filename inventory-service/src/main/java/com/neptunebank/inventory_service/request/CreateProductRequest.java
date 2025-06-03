package com.neptunebank.inventory_service.request;

import com.neptunebank.inventory_service.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private Integer quantity;
}
