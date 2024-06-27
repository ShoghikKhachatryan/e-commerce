package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.CreateProductDto;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdateProductDto;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final String PRODUCT_PATH = "/products";

    private static final String PRODUCT_UUID_PATH = "/{productUuid}";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductDto createProductDto) {
        final var productDto = productService.createProduct(createProductDto);

        URI location = URI.create(PRODUCT_PATH + "/" + productDto.getId());
        return ResponseEntity.created(location).body(productDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable UUID productUuid) {
        return productService.getProduct(productUuid);
    }

    @PutMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(
            @PathVariable UUID productUuid,
            @Valid @RequestBody UpdateProductDto updateProductDto
    ) {
        final var productDto = productService.updateProduct(productUuid, updateProductDto);
        return productDto;
    }

    @DeleteMapping(PRODUCT_UUID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID productUuid) {
        productService.deleteProductById(productUuid);
    }
}
