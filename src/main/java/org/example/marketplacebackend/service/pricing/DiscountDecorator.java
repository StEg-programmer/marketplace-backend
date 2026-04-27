package org.example.marketplacebackend.service.pricing;

import org.example.marketplacebackend.domain.catalog.Product;
import org.hibernate.annotations.DialectOverride;

import java.math.BigDecimal;

public class DiscountDecorator extends PriceCalculatorDecorator {
    public DiscountDecorator(PriceCalculator delegate) {
        super(delegate);
    }

    @Override
    public BigDecimal calculate(Product product,int quantity,String deliveryType) {
        BigDecimal price = delegate.calculate(product,quantity,deliveryType);

        Integer discount = product.getDiscountPercent();
        if (discount == null || discount < 0) {
            return price;
        }

        BigDecimal discounteValue = price.multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100));

        return price.subtract(discounteValue);
    }
}
