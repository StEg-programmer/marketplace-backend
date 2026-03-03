package org.example.marketplacebackend.service;

import jakarta.transaction.Transactional;
import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.example.marketplacebackend.domain.order.Payment;
import org.example.marketplacebackend.dto.CheckoutResponse;
import org.example.marketplacebackend.repository.OrderRepository;
import org.example.marketplacebackend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutProcessor {
    private final OrderRepository _orderRepository;
    private final PaymentRepository _paymentRepository;
    private final DigitalFulfillmentService _digitalFulfillmentService;

    public CheckoutProcessor(OrderRepository orderRepository, PaymentRepository paymentRepository, DigitalFulfillmentService digitalFulfillmentService) {
        this._orderRepository = orderRepository;
        this._paymentRepository = paymentRepository;
        this._digitalFulfillmentService = digitalFulfillmentService;
    }

    @Transactional
    public CheckoutResponse process(Order order) {
        _orderRepository.save(order);

        Payment payment = new Payment(
                order.getId(),
                "MOCK_PROVIDER",
                Payment.PaymentStatus.SUCCESS,
                order.getTotalAmount()
        );

        _paymentRepository.save(payment);

        order.markPaid();

        List<CheckoutResponse.DigitalKeyIssued> issueds = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            if (item.getKind() == OrderItem.ItemKind.DIGITAL){
                List<DigitalKey> keys = _digitalFulfillmentService.issueKeysForItem(item);
                for (DigitalKey key : keys) {
                    issueds.add(new CheckoutResponse.DigitalKeyIssued(item.getProductId(),key.getKeyValue()));
                }
            }
        }

        CheckoutResponse resp = new CheckoutResponse();
        resp.orderId = order.getId();
        resp.issuedKeys = issueds;
        resp.paymentStatus = "PAID";
        resp.totalAmount = order.getTotalAmount();
        return resp;
    }
}
