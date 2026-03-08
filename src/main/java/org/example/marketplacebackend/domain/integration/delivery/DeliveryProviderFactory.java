package org.example.marketplacebackend.domain.integration.delivery;

public interface DeliveryProviderFactory {
    DeliveryProvider create(DeliveryType type);
}
