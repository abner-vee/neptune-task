package com.neptunebank.inventory_service.service.impl;

import com.neptunebank.inventory_service.InventoryProto;
import com.neptunebank.inventory_service.InventoryServiceGrpc;
import com.neptunebank.inventory_service.entiy.Product;
import com.neptunebank.inventory_service.enums.ProductStatus;
import com.neptunebank.inventory_service.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

@GrpcService
public class InventoryServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void checkAndUpdateStock(InventoryProto.OrderRequest request,
                                    StreamObserver<InventoryProto.OrderResponse> responseObserver) {
        log.info("Checking stock for product: {}, quantity: {}", request.getProduct(), request.getQuantity());

        Optional<Product> productOpt = productRepository.findProductByNameIgnoreCase(request.getProduct());
        if (productOpt.isEmpty()) {
            log.info("Product not found: " + request.getProduct());
            responseObserver.onNext(InventoryProto.OrderResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Product not found: " + request.getProduct())
                    .build());
            responseObserver.onCompleted();
            return;
        }

        Product product = productOpt.get();

        if (product.getQuantity() >= request.getQuantity()) {
            product.setQuantity(product.getQuantity() - request.getQuantity());
            if(product.getQuantity() < 1){
                product.setStatus(ProductStatus.OUT_OF_STOCK);
            }
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
            log.info("Stock updated successfully.");
            responseObserver.onNext(InventoryProto.OrderResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Stock updated successfully.")
                    .build());
        } else {
            log.info("Insufficient stock.");
            responseObserver.onNext(InventoryProto.OrderResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Insufficient stock.")
                    .build());
        }

        responseObserver.onCompleted();
    }
}
