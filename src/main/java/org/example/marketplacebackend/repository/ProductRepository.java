package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.catalog.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}