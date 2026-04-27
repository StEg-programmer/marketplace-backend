package org.example.marketplacebackend.integration.adapter;

import org.example.marketplacebackend.domain.integration.payment.PaymentResult;
import org.example.marketplacebackend.integration.external.MockPaymentGateway;

import java.math.BigDecimal;

public class PaymentGatewayAdapter {

    private final MockPaymentGateway _paymentGateway;

    public PaymentGatewayAdapter(MockPaymentGateway paymentGateway) {
        this._paymentGateway = paymentGateway;
    }

    public PaymentResult pay(Long orderId, BigDecimal amount, String channelName) {
        MockPaymentGateway.GatewayPaymentResponse response =
                _paymentGateway.executeTransaction("order-"+orderId,
                                                    amount.doubleValue(),
                                                    channelName);
        return new PaymentResult(
                response.approved(),
                channelName,
                response.description()
        );
    }
}
