package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository mockedProductRepository;

    @InjectMocks
    private ProductService mockedProductService;

    private Product product = Product.builder()
            .id(UUID.fromString("8eafdf29-ef2e-4872-9a27-83bee1f3d92c"))
            .name("TV")
            .price(BigDecimal.valueOf(100.5))
            .build();

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
    void getProductByExistedId() {
        UUID id = product.getId();
        when(mockedProductRepository.findById(id)).thenReturn(Optional.of(product));

        mockedProductService.getProduct(id);

        verify(mockedProductRepository).findById(id);
    }

    @Test
    void getProductByNotExsitedId() {
        UUID id = product.getId();

        Assertions.assertThrows(NoSuchElementException.class,
                () -> mockedProductService.getProduct(id));

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
    void getAllProducts() {
        when(mockedProductRepository.findAll()).thenReturn(List.of(product));

        mockedProductService.getProducts();

        verify(mockedProductRepository).findAll();
    }

    @Test
    void updateProductByExsitedId() {
        when(mockedProductRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(mockedProductRepository.save(product)).thenReturn(product);

        mockedProductService.updateProduct(product);

        verify(mockedProductRepository).save(product);
        verify(mockedProductRepository).findById(product.getId());
    }

    @Test
    void updateProductByNotExistedId() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> mockedProductService.updateProduct(product));

        verify(mockedProductRepository).findById(product.getId());
    }
}