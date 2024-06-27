package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.CreateProductDto;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdateProductDto;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest extends BaseControllerTest {

    @MockBean
    private ProductService productService;

    final UUID id = UUID.fromString("8eafdf29-ef2e-4872-9a27-83bee1f3d92c");

    private final ProductDto productDto = new ProductDto(id, "TV", BigDecimal.valueOf(4.00));

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void crateProductsByValidReturnsExpectedResult() throws Exception {
        final String jsonContent = "{"+
                "\"name\": \"TV\"," +
                "\"price\": 4.00" +
                "}";

        CreateProductDto createProductDto = new CreateProductDto("TV", BigDecimal.valueOf(4.00));
        doReturn(productDto).when(productService).createProduct(createProductDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/products/" + id))
                .andExpect(content().json(jsonContent));

        verify(productService).createProduct(createProductDto);
    }

    @Test
    public void crateProductsByExsitedNameReturnsExpectedResult() throws Exception {
        CreateProductDto createProductDto = new CreateProductDto("TV", BigDecimal.valueOf(4.00));
        String name = createProductDto.getName();
        doThrow(new EntityByGivenNameExistException(name)).when(productService).createProduct(createProductDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.errorType").value("ENTITY_ALREADY_EXIST"))
                .andExpect(jsonPath("$.message").value("Entity with name '" + name + "' already exist."));
        verify(productService).createProduct(createProductDto);
    }

    @Test
    public void updatePriceProductsByValidIdReturnsExpectedResult() throws Exception {
        UpdateProductDto updateProductDto = new UpdateProductDto(BigDecimal.valueOf(9.4));

        final String updateJsonContent = "{"+
                "\"name\": \"TV\"," +
                "\"price\": 9.4" +
                "}";

        productDto.setPrice(updateProductDto.getPrice());

        doReturn(productDto).when(productService).updateProduct(id, updateProductDto);

        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(updateJsonContent));
        verify(productService).updateProduct(id, updateProductDto);
    }

    @Test
    public void updatePriceProductByGivenInValidIdReturnsExpectedResult() throws Exception {
        UpdateProductDto updateProductDto = new UpdateProductDto(BigDecimal.valueOf(9.4));

        doThrow(new EntityNotFoundException(id)).when(productService).updateProduct(id, updateProductDto);

        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(productService).updateProduct(id, updateProductDto);
    }

    @Test
    public void getProductsReturnsExpectedResult() throws Exception {
        final String jsonContentList = "[{"+
                "\"name\": \"TV\"," +
                "\"price\": 4.00" +
                "}]";

        doReturn(List.of(productDto)).when(productService).getProducts();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContentList));

        verify(productService).getProducts();
    }

    @Test
    public void getProductByGivenValidIdReturnsExpectedResult() throws Exception {
        final String jsonContent = "{"+
                "\"name\": \"TV\"," +
                "\"price\": 4.00" +
                "}";

        doReturn(productDto).when(productService).getProduct(id);

        mockMvc.perform(get("/products/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(productService).getProduct(id);
    }

    @Test
    public void getProductByGivenInValidIdReturnsExpectedResult() throws Exception {

        // Mock the service call to throw an exception for an invalid ID
        doThrow(new EntityNotFoundException(id)).when(productService).getProduct(id);

        mockMvc.perform(get("/products/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(productService).getProduct(id);
    }

    @Test
    public void deleteProductByGivenValidIdReturnsExpectResult() throws Exception {
        doNothing().when(productService).deleteProductById(id);

        mockMvc.perform(delete("/products/" + id))
                .andExpect(status().isOk());

        verify(productService).deleteProductById(id);
    }

    @Test
    public void deleteProductByGivenInValidIdReturnsExpectResult() throws Exception {
        doThrow(new EntityNotFoundException(id)).when(productService).deleteProductById(id);
        mockMvc.perform(delete("/products/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(productService).deleteProductById(id);
    }
}
