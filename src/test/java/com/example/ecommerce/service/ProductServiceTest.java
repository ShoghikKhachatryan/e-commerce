package com.example.ecommerce.service;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdatePriceProductDto;
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

    final private Product product = Product.builder()
            .id(UUID.fromString("8eafdf29-ef2e-4872-9a27-83bee1f3d92c"))
            .name("TV")
            .price(BigDecimal.valueOf(100.5))
            .build();

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockedProductRepository);
    }

    @Test
    void createProductWithInvalidName() {
        ProductDto productDto = new ProductDto("TV", BigDecimal.valueOf(100.5));

        String name = productDto.getName();
        when(mockedProductRepository.findProductByName(name)).thenReturn(product);

        Assertions.assertThrows(EntityByGivenNameExistException.class,
                () -> mockedProductService.createProduct(productDto));

        verify(mockedProductRepository).findProductByName(name);
    }

    @Test
    void createProductWithUniqueName() {
        ProductDto productDto = new ProductDto("TV", BigDecimal.valueOf(100.5));

        when(mockedProductRepository.findProductByName(productDto.getName())).thenReturn(null);
        when(mockedProductRepository.save(any(Product.class))).thenReturn(product);


        mockedProductService.createProduct(productDto);

        verify(mockedProductRepository).findProductByName(productDto.getName());
        verify(mockedProductRepository).save(any(Product.class));
    }

    @Test
    void deleteProductById() {
        UUID id = product.getId();
        doNothing().when(mockedProductRepository).deleteById(id);
        when(mockedProductRepository.findById(id)).thenReturn(Optional.of(product));

        mockedProductService.deleteProductById(id);

        verify(mockedProductRepository).findById(id);
        verify(mockedProductRepository).deleteById(id);
    }

    @Test
    void deleteProductByInvalidId() {
        UUID id = product.getId();

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedProductService.deleteProductById(id));

        verify(mockedProductRepository).findById(id);
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

        Assertions.assertThrows(EntityNotFoundException.class,
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
        UpdatePriceProductDto updatePriceProductDto = new UpdatePriceProductDto(BigDecimal.valueOf(90.5));
        UUID id = product.getId();
        when(mockedProductRepository.findById(id)).thenReturn(Optional.ofNullable(product));
        doNothing().when(mockedProductRepository).updateProductPriceById(id, updatePriceProductDto.getPrice());

        mockedProductService.updateProduct(product.getId(), updatePriceProductDto);

        verify(mockedProductRepository).updateProductPriceById(id, updatePriceProductDto.getPrice());
        verify(mockedProductRepository).findById(product.getId());
    }

    @Test
    void updateProductByNotExistedId() {
        UUID id = product.getId();
        UpdatePriceProductDto updatePriceProductDto = new UpdatePriceProductDto(BigDecimal.valueOf(90.5));
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockedProductService.updateProduct(id, updatePriceProductDto));

        verify(mockedProductRepository).findById(id);
    }
}