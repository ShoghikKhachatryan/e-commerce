package com.example.ecommerce.service;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdatePriceProductDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if (productRepository.findProductByName(productDto.getName()) != null) {
            throw new EntityByGivenNameExistException(productDto.getName());
        }

        Product product = mapFromProductDtoToProduct(productDto);
        productRepository.save(product);
        return productDto;
    }

    @Transactional
    public void deleteProductById(UUID id) {
        getProduct(id);
        productRepository.deleteById(id);
    }

    public ProductDto getProduct(UUID id) {
        return mapFromProductToProductDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    public ProductDto getProduct(String name) {
        return mapFromProductToProductDto(productRepository.findProductByName(name));
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(product -> mapFromProductToProductDto(product)).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateProduct(UUID id, UpdatePriceProductDto updatePriceProductDto) {
        final var productDto = getProduct(id);
        productRepository.updateProductPriceById(id, updatePriceProductDto.getPrice());
        productDto.setPrice(updatePriceProductDto.getPrice());
        return productDto;
    }

    private ProductDto mapFromProductToProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    private Product mapFromProductDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
    }
}
