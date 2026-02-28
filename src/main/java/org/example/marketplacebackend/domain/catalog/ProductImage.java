package org.example.marketplacebackend.domain.catalog;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private int position = 0;

    protected ProductImage() {}

    public ProductImage(Product product, String imageUrl, int position) {
        this.product = product;
        this.imageUrl = imageUrl;
        this.position = position;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public String getImageUrl() { return imageUrl; }
    public int getPosition() { return position; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImage that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}