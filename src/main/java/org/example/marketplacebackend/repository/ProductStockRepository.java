package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.catalog.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    Optional<ProductStock> findByProductId(Long productId);
}
