package com.example.ecommerce.service;

import com.example.ecommerce.dto.product.CreateProductDto;
import com.example.ecommerce.dto.product.UpdateProductDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository mockedProductRepository;

    @InjectMocks
    private ProductService mockedProductService;

    final UUID id = UUID.fromString("8eafdf29-ef2e-4872-9a27-83bee1f3d92c");
    final private Product product = new Product(id,"TV", BigDecimal.valueOf(100.5));

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedProductRepository);
    }

    @Test
    void createProductWithInvalidName() {
        CreateProductDto createProductDto = new CreateProductDto("TV", BigDecimal.valueOf(100.5));

        String name = createProductDto.getName();
        when(mockedProductRepository.existsByName(name)).thenReturn(true);

        Assertions.assertThrows(EntityByGivenNameExistException.class,
                () -> mockedProductService.createProduct(createProductDto));

        verify(mockedProductRepository).existsByName(name);
    }

    @Test
    void createProductWithUniqueName() {
        CreateProductDto createProductDto = new CreateProductDto("TV", BigDecimal.valueOf(100.5));

        String name = createProductDto.getName();

        when(mockedProductRepository.existsByName(name)).thenReturn(false);
        when(mockedProductRepository.save(any(Product.class))).thenReturn(product);

        mockedProductService.createProduct(createProductDto);

        verify(mockedProductRepository).existsByName(name);
        verify(mockedProductRepository).save(any(Product.class));
    }

    @Test
    void deleteProductById() {
        doNothing().when(mockedProductRepository).deleteById(id);
        when(mockedProductRepository.existsById(id)).thenReturn(true);

        mockedProductService.deleteProductById(id);

        verify(mockedProductRepository).existsById(id);
        verify(mockedProductRepository).deleteById(id);
    }

    @Test
    void deleteProductByInvalidId() {
        when(mockedProductRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedProductService.deleteProductById(id));

        verify(mockedProductRepository).existsById(id);
    }

    @Test
    void getProductByExistedId() {
        when(mockedProductRepository.findById(id)).thenReturn(Optional.of(product));

        mockedProductService.getProduct(id);

        verify(mockedProductRepository).findById(id);
    }

    @Test
    void getProductByNotExsitedId() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedProductService.getProduct(id));

        verify(mockedProductRepository).findById(id);
    }

    @Test
    void getProducts() {
        when(mockedProductRepository.findAll()).thenReturn(List.of(product));

        mockedProductService.getProducts();

        verify(mockedProductRepository).findAll();
    }

    @Test
    void updateProductByExsitedId() {
        UpdateProductDto updateProductDto = new UpdateProductDto(BigDecimal.valueOf(90.5));

        when(mockedProductRepository.findById(id)).thenReturn(Optional.of(product));
        doNothing().when(mockedProductRepository).updateProductPriceById(id, updateProductDto.getPrice());

        mockedProductService.updateProduct(id, updateProductDto);

        verify(mockedProductRepository).updateProductPriceById(id, updateProductDto.getPrice());
        verify(mockedProductRepository).findById(product.getId());
    }

    @Test
    void updateProductByNotExistedId() {
        UpdateProductDto updateProductDto = new UpdateProductDto(BigDecimal.valueOf(90.5));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedProductService.updateProduct(id, updateProductDto));

        verify(mockedProductRepository).findById(id);
    }
}