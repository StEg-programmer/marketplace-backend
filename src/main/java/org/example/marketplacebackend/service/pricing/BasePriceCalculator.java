package org.example.marketplacebackend.service.pricing;

import org.example.marketplacebackend.domain.catalog.Product;

import java.math.BigDecimal;

public class BasePriceCalculator implements PriceCalculator {
    @Override
    public BigDecimal calculate(Product product,int quantity,String deliveryType) {
        return product.getBasePrice().multiply(BigDecimal.valueOf(quantity));
    }
}
