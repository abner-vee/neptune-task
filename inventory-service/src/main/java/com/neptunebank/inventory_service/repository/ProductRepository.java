package com.neptunebank.inventory_service.repository;

import com.neptunebank.inventory_service.entiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByNameIgnoreCase(String productName);
}
