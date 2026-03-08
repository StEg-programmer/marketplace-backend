package org.example.marketplacebackend.domain.integration.payment;

import java.math.BigDecimal;

public interface PaymentProvider {
    PaymentResult pay(Long orderId, BigDecimal amount);
}

