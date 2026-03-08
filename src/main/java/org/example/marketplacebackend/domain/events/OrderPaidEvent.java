package org.example.marketplacebackend.domain.events;

public record OrderPaidEvent (Long orderId,Long buyerId, String paymetProvider) implements Event {

    @Override
    public String type() {
        return "ORDER_PAID";
    }
}

