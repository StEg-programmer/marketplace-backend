package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.catalog.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findAllByProductIdOrderByPositionAsc(Long productId);
}