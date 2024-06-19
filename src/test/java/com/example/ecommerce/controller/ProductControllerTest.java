package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.UpdatePriceProductDto;
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

    private final UUID id = UUID.randomUUID();

    private final ProductDto productDto = new ProductDto("TV", BigDecimal.valueOf(4.00));

    private final String jsonContentList = "[{"+
            "\"name\": \"TV\"," +
            "\"price\": 4.00" +
            "}]";

    private final String jsonContent = "{"+
            "\"name\": \"TV\"," +
            "\"price\": 4.00" +
            "}";

    private final String updateJsonContent = "{"+
            "\"name\": \"TV\"," +
            "\"price\": 9.4" +
            "}";

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void getProductsReturnsExpectedResult() throws Exception {
        doReturn(List.of(productDto)).when(productService).getProducts();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContentList));
            verify(productService).getProducts();
    }

    @Test
    public void crateProductsByValidReturnsExpectedResult() throws Exception {
        doReturn(productDto).when(productService).createProduct(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/products"))
                .andExpect(content().json(jsonContent));
        verify(productService).createProduct(productDto);
    }

    @Test
    public void crateProductsByExsitedNameReturnsExpectedResult() throws Exception {
        String name = productDto.getName();
        doThrow(new EntityByGivenNameExistException(name)).when(productService).createProduct(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.errorType").value("ALREADY_ENTITY_EXIST"))
                .andExpect(jsonPath("$.message").value("Entity with name '" + name + "' already exist."));
        verify(productService).createProduct(productDto);
    }

    @Test
    public void updatePriceProductsByValidIdReturnsExpectedResult() throws Exception {
        UpdatePriceProductDto updatePriceProductDto = new  UpdatePriceProductDto(BigDecimal.valueOf(9.4));

        productDto.setPrice(updatePriceProductDto.getPrice());

        doReturn(productDto).when(productService).updateProduct(id, updatePriceProductDto);

        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePriceProductDto)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/products"))
                .andExpect(content().json(updateJsonContent));
        verify(productService).updateProduct(id, updatePriceProductDto);
    }

    @Test
    public void updatePriceProductByGivenInValidIdReturnsExpectedResult() throws Exception {
        UpdatePriceProductDto updatePriceProductDto = new  UpdatePriceProductDto(BigDecimal.valueOf(9.4));

        doThrow(new EntityNotFoundException(id)).when(productService).updateProduct(id, updatePriceProductDto);

        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePriceProductDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(productService).updateProduct(id, updatePriceProductDto);
    }

    @Test
    public void getProductByGivenValidIdReturnsExpectedResult() throws Exception {
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
