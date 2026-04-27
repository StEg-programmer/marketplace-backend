package org.example.marketplacebackend.service;

import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.domain.order.OrderBuilder;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.springframework.stereotype.Service;
import org.example.marketplacebackend.service.pricing.BasePriceCalculator;
import org.example.marketplacebackend.service.pricing.DeliveryCostDecorator;
import org.example.marketplacebackend.service.pricing.DiscountDecorator;
import org.example.marketplacebackend.service.pricing.PriceCalculator;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderAssembler {


    public Order build(Long buyerId, List<ProductQty> items, String paymentType, String deliveryType) {
        OrderBuilder builder = new OrderBuilder(buyerId)
                .withPaymentType(paymentType)
                .withDeliveryType(deliveryType);

        for (ProductQty pq : items) {
            Product p = pq.product();
            PriceCalculator calculator = new BasePriceCalculator();
            calculator = new DiscountDecorator(calculator);
            calculator = new DeliveryCostDecorator(calculator);

            BigDecimal totalPrice = calculator.calculate(p,pq.qty(),deliveryType);
            BigDecimal unitPrice = totalPrice.divide(BigDecimal.valueOf(pq.qty()), 2, java.math.RoundingMode.HALF_UP);

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