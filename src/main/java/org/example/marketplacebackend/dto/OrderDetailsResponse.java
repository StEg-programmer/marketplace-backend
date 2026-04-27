package org.example.marketplacebackend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsResponse {

    public Long orderId;
    public Long buyerId;
    public String status;
    public String paymentStatus;
    public BigDecimal totalAmount;
    public String paymentType;
    public String deliveryType;
    public Instant createdAt;

    public List<DetatilsItem> items = new ArrayList<>();

    public static class DetatilsItem{
        public Long orderItemId;
        public Long productId;
        public String title;
        public BigDecimal price;
        public int quantity;
        public BigDecimal unitPrice;
        public BigDecimal lineTotal;
        public String kind;
        public List<String> digitalKeys = new ArrayList<>();

        public String fulfillmentType;
        public String fulfillmentStatus;
        public String fulfillmentMessage;
    }
}
