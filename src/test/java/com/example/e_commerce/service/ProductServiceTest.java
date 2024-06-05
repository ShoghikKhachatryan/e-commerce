package com.example.e_commerce.service;

import com.example.e_commerce.entity.Product;
import com.example.e_commerce.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository mockedProductRepository;

    @InjectMocks
    private ProductService mockedProductService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("TV");
        product.setPrice(BigDecimal.valueOf(100.5));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedProductRepository);
    }

    @Test
    void createProduct() {
        when(mockedProductRepository.save(product)).thenReturn(product);

        mockedProductService.createProduct(product);

        verify(mockedProductRepository).save(product);
    }

    @Test
    void deleteProductById() {
        UUID id = product.getId();
        doNothing().when(mockedProductRepository).deleteById(id);

        mockedProductService.deleteProductById(id);

        verify(mockedProductRepository).deleteById(id);
    }

    @Test
    void getProductById() {
        UUID id = product.getId();
        when(mockedProductRepository.findById(id)).thenReturn(Optional.of(product));

        mockedProductService.getProduct(id);

        verify(mockedProductRepository).findById(id);
    }

    @Test
    void getProductByName() {
        String name = product.getName();
        when(mockedProductRepository.findProductByName(name)).thenReturn(product);

        mockedProductService.getProduct(name);

        verify(mockedProductRepository).findProductByName(name);
    }

    @Test
    void getProducts() {
        when(mockedProductRepository.findAll()).thenReturn(List.of(product));

        mockedProductService.getProducts();

        verify(mockedProductRepository).findAll();
    }

    @Test
    void updateProduct() {
        when(mockedProductRepository.save(product)).thenReturn(product);

        mockedProductService.updateProduct(product);

        verify(mockedProductRepository).save(product);
    }
}