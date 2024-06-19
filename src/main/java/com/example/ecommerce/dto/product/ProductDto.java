package com.example.ecommerce.dto.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(0)
    private BigDecimal price;
}
