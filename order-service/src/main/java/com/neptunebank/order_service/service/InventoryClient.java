package com.neptunebank.order_service.service;

import com.neptunebank.order_service.InventoryProto;
import com.neptunebank.order_service.InventoryServiceGrpc;
import com.neptunebank.order_service.ProductProto;
import com.neptunebank.order_service.ProductServiceGrpc;
import com.neptunebank.order_service.entity.Order;
import com.neptunebank.order_service.repository.OrderRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryClient {

    private InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;
    private ProductServiceGrpc.ProductServiceBlockingStub productStub;
    @Autowired
    private OrderRepository orderRepository;

    @Value("${grpc.client.inventory-service.address}")
    private String inventoryServiceAddress;

    @Value("${grpc.client.inventory-service.negotiation-type}")
    private String negotiationType;

    @PostConstruct
    public void init() {
        String[] parts = inventoryServiceAddress.split("://");
        String hostAndPort = parts.length > 1 ? parts[1] : parts[0];
        String host = hostAndPort.split(":")[0];
        int port = Integer.parseInt(hostAndPort.split(":")[1]);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // .useTransportSecurity() if TLS
                .build();

        this.inventoryStub = InventoryServiceGrpc.newBlockingStub(channel);
        this.productStub = ProductServiceGrpc.newBlockingStub(channel);

        System.out.println("Stubs initialized: inventoryStub = " + (inventoryStub != null)
                + ", productStub = " + (productStub != null));
    }


    public List<ProductProto.ProductDetails> getAllProducts(int page, int limit) {
        if (productStub == null) {
            throw new IllegalStateException("ProductService stub not initialized.");
        }

        ProductProto.ProductListResponse response = productStub.getAllProducts(
                ProductProto.GetAllProductRequest.newBuilder().setPage(page).setLimit(limit).build()
        );
        return response.getProductDetailsList();
    }

    public ProductProto.ProductDetails getProductById(Long id) {
        if (productStub == null) {
            throw new IllegalStateException("ProductService stub not initialized.");
        }

        return productStub.getProductById(
                ProductProto.GetProductByIdRequest.newBuilder().setId(id).build()
        );
    }

    public ProductProto.ProductDetails createProduct(String name, int quantity) {
        if (productStub == null) {
            throw new IllegalStateException("ProductService stub not initialized.");
        }

        return productStub.createProducts(
                ProductProto.CreateProduct.newBuilder()
                        .setProduct(name)
                        .setQuantity(quantity)
                        .build()
        );
    }


    public InventoryProto.OrderResponse createOrder(String product, int quantity) {
        if (inventoryStub == null) {
            throw new IllegalStateException("InventoryService stub not initialized.");
        }

        Order newOrder = new Order();
        newOrder.setProduct(product);
        newOrder.setQuantity(quantity);
        newOrder.setStatus(Order.Status.PENDING);
        Order savedOrder = orderRepository.save(newOrder);


        InventoryProto.OrderResponse response = inventoryStub.checkAndUpdateStock(
                InventoryProto.OrderRequest.newBuilder()
                        .setProduct(product)
                        .setQuantity(quantity)
                        .build()
        );

        savedOrder.setStatus(response.getSuccess() ? Order.Status.CONFIRMED : Order.Status.REJECTED);
        orderRepository.save(savedOrder);

        return response;
    }

    public ProductProto.DeleteProductByIdResponse deleteProductByI(Long productId){
        if (inventoryStub == null) {
            throw new IllegalStateException("InventoryService stub not initialized.");
        }

        return productStub.deleteProductById(
                ProductProto.DeleteProductByIdRequest.newBuilder()
                        .setId(productId)
                        .build()
        );
    }

}
