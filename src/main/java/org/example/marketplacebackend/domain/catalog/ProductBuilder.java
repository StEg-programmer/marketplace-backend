package org.example.marketplacebackend.domain.catalog;

import java.math.BigDecimal;

public class ProductBuilder {
    private final Product product;

    private ProductBuilder(Long sellerId, Category category, String title, BigDecimal basePrice, Product.ProductType productType) {
        this.product = new Product();
        this.product.setSellerId(sellerId);
        this.product.setCategory(category);
        this.product.setTitle(title);
        this.product.setBasePrice(basePrice);
        this.product.setType(productType);
    }

    public static ProductBuilder create(Long sellerId, Category category, String title, BigDecimal basePrice, Product.ProductType productType) {
        if (sellerId == null)throw new IllegalArgumentException("sellerId is required");
        if (category == null)throw new IllegalArgumentException("category is required");
        if (title == null && title.isBlank())throw new IllegalArgumentException("title is required");
        if (basePrice == null || basePrice.signum() < 1)throw new IllegalArgumentException("basePrice is required and must be > 0");
        if (productType == null)throw new IllegalArgumentException("productType is required");
        return new ProductBuilder(sellerId, category, title, basePrice, productType);

    }

    public ProductBuilder withDescription(String description){
        this.product.setDescription(description);
        return this;
    }

    public ProductBuilder withDescounterPercent(Integer descounterPercent){
        this.product.setDiscountPercent(descounterPercent);
        return this;
    }

    public ProductBuilder withAttributesJson(String attributesJson){
        this.product.setAttributesJson(attributesJson);
        return this;
    }

    public ProductBuilder asActive(){
        this.product.setStatus(Product.ProductStatus.ACTIVE);
        return this;
    }

    public Product build(){
        return this.product;
    }
}

