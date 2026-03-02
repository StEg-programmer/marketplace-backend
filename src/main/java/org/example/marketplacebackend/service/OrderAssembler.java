package org.example.marketplacebackend.service;

import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.domain.order.OrderBuilder;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderAssembler {

    private final PriceCalculator priceCalculator;

    public OrderAssembler(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public Order build(Long buyerId, List<ProductQty> items) {
        OrderBuilder builder = new OrderBuilder(buyerId);

        for (ProductQty pq : items) {
            Product p = pq.product();
            BigDecimal unitPrice = priceCalculator.finalPrice(p);

            OrderItem item = new OrderItem(
                    p.getId(),
                    p.getTitle(),
                    unitPrice,
                    pq.qty(),
                    p.getType() == Product.ProductType.DIGITAL
                            ? OrderItem.ItemKind.DIGITAL
                            : OrderItem.ItemKind.PHYSICAL
            );

            builder.addItem(item);
        }

        return builder.build();
    }

    public record ProductQty(Product product, int qty) {}
}