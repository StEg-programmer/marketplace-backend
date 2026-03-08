package org.example.marketplacebackend.domain.integration.delivery;

public record DeliveryResult (
        boolean succes,
        String providerName,
        String message
){}
