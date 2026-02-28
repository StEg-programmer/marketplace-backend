package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {

    List<DigitalKey> findTop100ByProductIdAndStatusOrderByIdAsc(Long productId, DigitalKey.KeyStatus status);

    long countByProductIdAndStatus(Long productId, DigitalKey.KeyStatus status);
}