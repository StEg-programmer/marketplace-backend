package org.example.marketplacebackend.domain.order;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_items")
public class OrderItem {

    public Long getId() {
        return id;
    }

    public enum ItemKind { PHYSICAL, DIGITAL }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "title_snapshot", nullable = false)
    private String titleSnapshot;

    @Column(name = "price_snapshot", nullable = false)
    BigDecimal priceSnapshot;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemKind kind;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected OrderItem() {}

    public OrderItem(Long productId, String titleSnapshot,
                     BigDecimal priceSnapshot, int quantity, ItemKind kind) {
        this.productId = productId;
        this.titleSnapshot = titleSnapshot;
        this.priceSnapshot = priceSnapshot;
        this.quantity = quantity;
        this.kind = kind;
    }

    void setOrder(Order order) {
        this.order = order;
    }

    public ItemKind getKind() { return kind; }
    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}