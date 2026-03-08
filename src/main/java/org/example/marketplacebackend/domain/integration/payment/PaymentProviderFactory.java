package org.example.marketplacebackend.domain.integration.payment;

public interface PaymentProviderFactory {
    PaymentProvider create(PaymentType type);
}
