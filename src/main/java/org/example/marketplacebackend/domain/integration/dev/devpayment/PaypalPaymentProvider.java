package org.example.marketplacebackend.domain.integration.dev.devpayment;

import org.example.marketplacebackend.domain.integration.payment.PaymentProvider;
import org.example.marketplacebackend.domain.integration.payment.PaymentResult;
import org.example.marketplacebackend.integration.adapter.PaymentGatewayAdapter;
import org.example.marketplacebackend.integration.external.MockPaymentGateway;

import java.math.BigDecimal;

public class PaypalPaymentProvider implements PaymentProvider {
    private final PaymentGatewayAdapter _paymentAdapter = new PaymentGatewayAdapter(new MockPaymentGateway());

    @Override
    public PaymentResult pay(Long orderId, BigDecimal amount){
        return _paymentAdapter.pay(orderId,amount,"PAYPAL_DEV");
    }
}
