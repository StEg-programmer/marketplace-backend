package org.example.marketplacebackend.dto;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutResponse {
    public Long orderId;
    public BigDecimal totalAmount;
    public String paymentStatus; // PAID

    public List<DigitalKeyIssued> issuedKeys;

    public static class DigitalKeyIssued {
        public Long productId;
        public String keyValue;

        public DigitalKeyIssued(Long productId, String keyValue) {
            this.productId = productId;
            this.keyValue = keyValue;
        }
    }
}