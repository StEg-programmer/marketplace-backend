package org.example.marketplacebackend.domain.order;

import java.math.BigDecimal;

public class OrderBuilder {

    private final Order order;
    private BigDecimal total = BigDecimal.ZERO;

    public OrderBuilder(Long buyerId) {
        this.order = new Order(buyerId);
    }

    public OrderBuilder addItem(OrderItem item) {
        order.addItem(item);
        total = total.add(
                item.getKind() == OrderItem.ItemKind.DIGITAL
                        ? item.priceSnapshot
                        : item.priceSnapshot.multiply(BigDecimal.valueOf(item.getQuantity()))
        );
        return this;
    }

    public Order build() {
        order.setTotalAmount(total);
        return order;
    }
}