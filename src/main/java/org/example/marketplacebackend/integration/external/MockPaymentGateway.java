package org.example.marketplacebackend.integration.external;

public class MockPaymentGateway {

    public GatewayPaymentResponse executeTransaction(String externalOrderRef,
                                                     double amount,
                                                     String paymentChannel) {
        return new GatewayPaymentResponse(
                true,
                "TXN-" + externalOrderRef,
                "Mock payment completed via " + paymentChannel
        );
    }

    public record GatewayPaymentResponse(
            boolean approved,
            String transactionId,
            String description
    ) {
    }
}
