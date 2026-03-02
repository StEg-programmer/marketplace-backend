package org.example.marketplacebackend.service;

import org.example.marketplacebackend.domain.catalog.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceCalculator {
    public BigDecimal finalPrice(Product product) {
        BigDecimal price = product.getBasePrice();
        Integer disc = product.getDiscountPercent();
        if (disc == null) return price;

        return price.subtract(
                price.multiply(BigDecimal.valueOf(disc))
                        .divide(BigDecimal.valueOf(100))
        );
    }
}
