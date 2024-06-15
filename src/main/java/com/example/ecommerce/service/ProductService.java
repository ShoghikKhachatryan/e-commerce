package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(UUID id) {
        getProduct(id);
        productRepository.deleteById(id);
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Product getProduct(String name) {
        return productRepository.findProductByName(name);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void updateProduct(Product product) {
        getProduct(product.getId());
        productRepository.save(product);
    }
}
