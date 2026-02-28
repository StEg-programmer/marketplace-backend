package org.example.marketplacebackend.domain.digital;


import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;



@Entity
@Table(name = "digital_keys")
public class DigitalKey {
    public enum KeyStatus{ AVAILABLE, RESERVED, SOLD}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id",nullable = false)
    private Long productId;

    @Column(name = "seller_id",nullable = false)
    private Long sellerId;

    @Column(name = "key_value",nullable = false,length = 500)
    private String keyValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private KeyStatus status = KeyStatus.AVAILABLE;

    @Column(name = "reserved_until")
    private Instant reservedUntil;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt =  Instant.now();

    protected DigitalKey() {}

    public DigitalKey(Long productId, Long sellerId, String keyValue) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.keyValue = keyValue;
        this.status = KeyStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public KeyStatus getStatus() {
        return status;
    }

    public Instant getReservedUntil() {
        return reservedUntil;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void markSold(Long orderItemId) {
        this.status = KeyStatus.SOLD;
        this.orderItemId = orderItemId;
        this.reservedUntil = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DigitalKey that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
