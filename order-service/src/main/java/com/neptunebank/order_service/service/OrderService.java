package com.neptunebank.order_service.service;

import com.neptunebank.order_service.entity.Order;
import com.neptunebank.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(String product, int quantity, Order.Status status) {
        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Order updateStatus(Order order, Order.Status status) {
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
