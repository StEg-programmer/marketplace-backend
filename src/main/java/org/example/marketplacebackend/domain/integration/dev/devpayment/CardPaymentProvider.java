package org.example.marketplacebackend.domain.integration.dev.devpayment;

import org.example.marketplacebackend.domain.integration.payment.PaymentProvider;
import org.example.marketplacebackend.domain.integration.payment.PaymentResult;

import java.math.BigDecimal;

public class CardPaymentProvider implements PaymentProvider {
    @Override
    public PaymentResult pay (Long orderId, BigDecimal amount) {
        return new PaymentResult(true,"CARD_DEV","Card payment simulated");
    }
}
