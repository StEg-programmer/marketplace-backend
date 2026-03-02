package org.example.marketplacebackend.repository;

import org.example.marketplacebackend.domain.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
