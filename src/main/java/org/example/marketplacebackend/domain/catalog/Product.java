package org.example.marketplacebackend.domain.catalog;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    public enum ProductType { PHYSICAL, DIGITAL }
    public enum ProductStatus { DRAFT, ACTIVE, BLOCKED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // пока seller_id как Long (User entity сделаем позже)
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "discount_percent")
    private Integer discountPercent; // nullable -> скидки нет

    @Column(name = "attributes_json", columnDefinition = "TEXT")
    private String attributesJson; // пока строка (потом можно JSON)

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Product() {}

    public Long getId() { return id; }
    public Long getSellerId() { return sellerId; }
    public Category getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ProductType getType() { return type; }
    public ProductStatus getStatus() { return status; }
    public BigDecimal getBasePrice() { return basePrice; }
    public Integer getDiscountPercent() { return discountPercent; }
    public String getAttributesJson() { return attributesJson; }
    public Instant getCreatedAt() { return createdAt; }

    public void setStatus(ProductStatus status) { this.status = status; }

    // package-private setters (для Builder, который будет в том же пакете)
    void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    void setCategory(Category category) { this.category = category; }
    void setTitle(String title) { this.title = title; }
    void setDescription(String description) { this.description = description; }
    void setType(ProductType type) { this.type = type; }
    void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }
    void setAttributesJson(String attributesJson) { this.attributesJson = attributesJson; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}