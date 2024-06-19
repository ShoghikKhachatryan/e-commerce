package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(0)
    private BigDecimal price;
}
