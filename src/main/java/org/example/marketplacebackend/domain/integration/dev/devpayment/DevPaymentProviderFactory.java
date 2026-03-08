package org.example.marketplacebackend.domain.integration.dev.devpayment;

import org.example.marketplacebackend.domain.integration.payment.PaymentProvider;
import org.example.marketplacebackend.domain.integration.payment.PaymentProviderFactory;
import org.example.marketplacebackend.domain.integration.payment.PaymentType;

public class DevPaymentProviderFactory implements PaymentProviderFactory {
    @Override
    public PaymentProvider create(PaymentType type){
        return switch(type){
            case CARD -> new CardPaymentProvider();
            case PAYPAL -> new PaypalPaymentProvider();
            case CASH -> new CashPaymentProvider();
        };
    }
}
