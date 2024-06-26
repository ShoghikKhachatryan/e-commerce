package com.example.ecommerce.service;

import com.example.ecommerce.dto.product.CreateProductDto;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdateProductDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDto createProduct(CreateProductDto createProductDto) {
        String name = createProductDto.getName();

        if (productRepository.existsByName(name)) {
            throw new EntityByGivenNameExistException(name);
        }

        Product product = mapToEntity(createProductDto);
        productRepository.save(product);
        return mapToDto(product);
    }

    @Transactional
    public void deleteProductById(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    public ProductDto getProduct(UUID id) {
        return mapToDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateProduct(UUID id, UpdateProductDto updateProductDto) {

        final var productDto = getProduct(id);
        productRepository.updateProductPriceById(id, updateProductDto.getPrice());
        productDto.setPrice(updateProductDto.getPrice());
        return productDto;
    }

    private ProductDto mapToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    private Product mapToEntity(CreateProductDto productDto) {
        return new Product(UUID.randomUUID(), productDto.getName(), productDto.getPrice());
    }
}
