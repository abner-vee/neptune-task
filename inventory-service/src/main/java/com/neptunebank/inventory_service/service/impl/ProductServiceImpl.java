package com.neptunebank.inventory_service.service.impl;

import com.neptunebank.inventory_service.InventoryProto;
import com.neptunebank.inventory_service.ProductProto;
import com.neptunebank.inventory_service.ProductServiceGrpc;
import com.neptunebank.inventory_service.entiy.Product;
import com.neptunebank.inventory_service.enums.ProductStatus;
import com.neptunebank.inventory_service.repository.ProductRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@GrpcService
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProducts(ProductProto.CreateProduct request,
                               StreamObserver<ProductProto.ProductDetails> responseObserver) {
        log.info("Creating product: {}, quantity: {}", request.getProduct(), request.getQuantity());

        Product product = new Product();
        product.setName(request.getProduct());
        product.setQuantity(request.getQuantity());
        product.setStatus(request.getQuantity() > 0 ? ProductStatus.IN_STOCK : ProductStatus.OUT_OF_STOCK);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product saved = productRepository.save(product);

        ProductProto.ProductDetails response = mapToProto(saved);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProducts(ProductProto.Empty request,
                               StreamObserver<ProductProto.ProductListResponse> responseObserver) {
        List<Product> products = productRepository.findAll();
        ProductProto.ProductListResponse.Builder builder = ProductProto.ProductListResponse.newBuilder();

        products.forEach(product -> builder.addProductDetails(mapToProto(product)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductById(ProductProto.GetProductByIdRequest request,
                               StreamObserver<ProductProto.ProductDetails> responseObserver) {
        Optional<Product> productOpt = productRepository.findById(request.getId());
        if (productOpt.isPresent()) {
            responseObserver.onNext(mapToProto(productOpt.get()));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Product not found with ID: " + request.getId())
                    .asRuntimeException());
        }
    }

    @Override
    public void deleteProductById(ProductProto.DeleteProductByIdRequest request,
                                  StreamObserver<ProductProto.DeleteProductByIdResponse> responseObserver) {
        productRepository.deleteById(request.getId());
        ProductProto.DeleteProductByIdResponse response = ProductProto.DeleteProductByIdResponse
                .newBuilder()
                .setMessage("Deleted product with ID: " + request.getId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private ProductProto.ProductDetails mapToProto(Product product) {
        return ProductProto.ProductDetails.newBuilder()
                .setId(product.getId())
                .setName(product.getName())
                .setStatus(product.getStatus().name())
                .setQuantity(product.getQuantity())
                .build();
    }
}
