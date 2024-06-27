package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findProductByName(String name);

    Boolean existsByName(String name);

    @Modifying
    @Query("Update Product p set p.price = :price where p.id = :id")
    void updateProductPriceById(UUID id, BigDecimal price);
}
