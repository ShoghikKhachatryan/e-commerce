package com.example.e_commerce.service;

import com.example.e_commerce.entity.Product;
import com.example.e_commerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id).get();
    }

    public Product getProduct(String name) {
        return productRepository.findProductByName(name);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}
