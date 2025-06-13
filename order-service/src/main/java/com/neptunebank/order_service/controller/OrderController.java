package com.neptunebank.order_service.controller;

import com.neptunebank.order_service.entity.Order;
import com.neptunebank.order_service.payload.request.OrderRequest;
import com.neptunebank.order_service.payload.response.OrderResponse;
import com.neptunebank.order_service.service.InventoryClient;
import com.neptunebank.order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final InventoryClient client;
    private final OrderService orderService;

    public OrderController(InventoryClient client, OrderService orderService) {
        this.client = client;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        log.info("Product name for order: {}",request.getName());
        var protoResp = client.createOrder(request.getName(), request.getQuantity());
        OrderResponse response = OrderResponse.builder()
                .success(protoResp.getSuccess() ? "success" : "failed")
                .message(protoResp.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(
            @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        List<OrderResponse> responses = orderService.findAllOrders(page, limit).stream()
                .map(order -> OrderResponse.builder()
                        .success(order.getStatus() == Order.Status.CONFIRMED ? "success" : "failed")
                        .message("Order ID: " + order.getId() + " | Status: " + order.getStatus())
                        .id(order.getId())
                        .quantity(order.getQuantity())
                        .status(order.getStatus().name())
                        .name(order.getProduct())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") Long id) {
        return orderService.findById(id)
                .map(order -> ResponseEntity.ok(OrderResponse.builder()
                        .success(order.getStatus() == Order.Status.CONFIRMED ? "success" : "failed")
                        .message("Order Status: " + order.getStatus())
                        .id(order.getId())
                        .quantity(order.getQuantity())
                        .status(order.getStatus().name())
                                .name(order.getProduct())
                        .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(OrderResponse.builder()
                                .success("failed")
                                .message("Order not found with ID: " + id)
                                .build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable("id") Long id) {
        boolean deleted = orderService.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", deleted);
        result.put("message", deleted ? "Order deleted successfully." : "Order not found.");
        return ResponseEntity.ok(result);
    }
}
