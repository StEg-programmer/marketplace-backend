package org.example.marketplacebackend.domain.integration.delivery;

public interface DeliveryProvider {
    DeliveryResult arrange(Long orderId, DeliveryType deliveryType);
}
