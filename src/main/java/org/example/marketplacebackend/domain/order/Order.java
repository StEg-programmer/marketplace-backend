package org.example.marketplacebackend.domain.order;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    public enum OrderStatus {CREATED, COMPLETED, CANCELLED}
    public enum PaymentStatus {PENDING, PAID, FAILED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "delivery_type")
    private String deliveryType;

    protected Order() {}

    public Order(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void markPaid() {
        this.paymentStatus = PaymentStatus.PAID;
        this.status = OrderStatus.COMPLETED;
    }

    public Order copyAsDraft(){
        Order copy = new Order(this.buyerId);

        for (OrderItem item : this.items) {
            OrderItem clonedItem = new OrderItem(
                    item.getProductId(),
                    item.getTitleSnapshot(),
                    item.getPriceSnapshot(),
                    item.getQuantity(),
                    item.getKind()
            );
            copy.addItem(clonedItem);
        }

        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        for (OrderItem item : copy.getItems()) {
            java.math.BigDecimal line = item.getPriceSnapshot()
                    .multiply(java.math.BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(line);
        }
        copy.setTotalAmount(totalAmount);

        return copy;
    }

    public Long getId() { return id; }
    public Long getBuyerId() { return buyerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItem> getItems() { return items; }
    public String getPaymentType() {return paymentType;}
    public void setPaymentType(String paymentType) {this.paymentType = paymentType;}
    public String getDeliveryType() {return deliveryType;}
    public void setDeliveryType(String deliveryType) {this.deliveryType = deliveryType;}
    public PaymentStatus getPaymentStatus(){return paymentStatus;}
    public OrderStatus getStatus(){return status;}
    public Instant getCreatedAt(){return createdAt;}

}
