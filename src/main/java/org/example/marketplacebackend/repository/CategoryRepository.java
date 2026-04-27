package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
}