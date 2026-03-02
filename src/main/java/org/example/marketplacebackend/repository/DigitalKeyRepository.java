package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {

    List<DigitalKey> findTop100ByProductIdAndStatusOrderByIdAsc(Long productId, DigitalKey.KeyStatus status);

    @Query(value = """
        SELECT *
        FROM digital_keys
        WHERE product_id = :productId AND status = 'AVAILABLE'
        ORDER BY id
        LIMIT :limit
        FOR UPDATE
        """, nativeQuery = true)
    List<DigitalKey> lockNextAvailable(@Param("productId") Long productId,
                                       @Param("limit") int limit);

    long countByProductIdAndStatus(Long productId, DigitalKey.KeyStatus status);
}