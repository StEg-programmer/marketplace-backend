package org.example.marketplacebackend.domain.integration.payment;

public record PaymentResult (
    boolean succes,
    String providerName,
    String message
){}

