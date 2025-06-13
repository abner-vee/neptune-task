package com.neptunebank.order_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neptunebank.order_service.payload.request.Product;
import com.neptunebank.order_service.payload.response.ProductResponse;
import com.neptunebank.order_service.service.InventoryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final InventoryClient client;

    public ProductController(InventoryClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        List<ProductResponse> responses = client.getAllProducts(page, limit).stream()
                .map(proto -> ProductResponse.builder()
                        .id(proto.getId())
                        .name(proto.getName())
                        .status(proto.getStatus())
                        .quantity(proto.getQuantity())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        var proto = client.getProductById(id);
        ProductResponse response = ProductResponse.builder()
                .id(proto.getId())
                .name(proto.getName())
                .status(proto.getStatus())
                .quantity(proto.getQuantity())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProductById(@PathVariable("id") Long id) {
        var resp = client.deleteProductByI(id);
        return ResponseEntity.ok(Map.of("success",true, "message",resp.getMessage()));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody Product request) {
        var proto = client.createProduct(request.getName(), request.getQuantity());
        ProductResponse response = ProductResponse.builder()
                .id(proto.getId())
                .name(proto.getName())
                .status(proto.getStatus())
                .quantity(proto.getQuantity())
                .build();
        return ResponseEntity.ok(response);
    }
}
