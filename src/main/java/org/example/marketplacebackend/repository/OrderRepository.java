package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
