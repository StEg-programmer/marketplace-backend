package org.example.marketplacebackend.service.pricing;

import org.example.marketplacebackend.domain.catalog.Product;

import java.math.BigDecimal;

public interface PriceCalculator {
    BigDecimal calculate(Product product,int quantity,String deliveryType);
}
