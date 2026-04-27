package org.example.marketplacebackend.service.pricing;

import org.example.marketplacebackend.domain.catalog.Product;
import org.hibernate.annotations.DialectOverride;

import java.math.BigDecimal;

public class DeliveryCostDecorator extends PriceCalculatorDecorator {
    public DeliveryCostDecorator(PriceCalculator delegate) {
        super(delegate);
    }

    @Override
    public BigDecimal calculate(Product product, int quantity, String deliveryType) {
        BigDecimal price = delegate.calculate(product, quantity, deliveryType);

        if (product.getType() == Product.ProductType.DIGITAL) {
            return price;
        }

        if (deliveryType == null || deliveryType.equals("NONE")) {
            return price;
        }

        BigDecimal deliveryCost = switch (deliveryType) {
            case "COURIER" -> BigDecimal.valueOf(10);
            case "PICKUP" -> BigDecimal.valueOf(3);
            case "POST" -> BigDecimal.valueOf(5);
            default -> BigDecimal.ZERO;
        };

        return price.add(deliveryCost);
    }
}