package org.example.marketplacebackend.service.pricing;

import org.example.marketplacebackend.domain.catalog.Product;

import java.math.BigDecimal;

public abstract class PriceCalculatorDecorator implements PriceCalculator {
    protected final PriceCalculator delegate;

    protected PriceCalculatorDecorator(PriceCalculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public BigDecimal calculate(Product product,int quantity,String deliveryType) {
        return delegate.calculate(product,quantity,deliveryType);
    }
}
