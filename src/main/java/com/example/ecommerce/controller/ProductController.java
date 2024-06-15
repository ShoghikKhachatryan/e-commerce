package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final String PRODUCTS_PATH = "/products";

    private static final String PRODUCT_UUID_PATH = "/{productUuid}";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping(PRODUCT_UUID_PATH)
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @DeleteMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID productUuid) {
        productService.deleteProductById(productUuid);
    }
}
