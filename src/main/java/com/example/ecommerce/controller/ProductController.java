package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdatePriceProductDto;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private static final String PRODUCT_PATH = "/products";

    private static final String PRODUCT_UUID_PATH = "/{productUuid}";

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        final var product = productService.createProduct(productDto);

        // Assuming the location URI is determined by the product ID or some other logic
        URI location = URI.create(String.format(PRODUCT_PATH));
        return ResponseEntity.created(location).body(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID productUuid) {
        return ResponseEntity.ok(productService.getProduct(productUuid));
    }

    @PutMapping(PRODUCT_UUID_PATH)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID productUuid, @Valid @RequestBody UpdatePriceProductDto updatePriceProductDto) {
        final var productDto = productService.updateProduct(productUuid, updatePriceProductDto);
        // Assuming the location URI is determined by the product ID or some other logic
        URI location = URI.create(String.format(PRODUCT_PATH));
        return ResponseEntity.status(HttpStatus.OK).location(location).body(productDto);
    }

    @DeleteMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID productUuid) {
        productService.deleteProductById(productUuid);
    }
}
