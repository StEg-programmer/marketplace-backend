package org.example.marketplacebackend.service;

import jakarta.transaction.Transactional;
import org.example.marketplacebackend.api.exception.BadRequestException;
import org.example.marketplacebackend.api.exception.NotFoundException;
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
public class BuyAgainService {

    private final OrderRepository _orderRepository;
    private final PaymentRepository _paymentRepository;
    private final DigitalFulfillmentService _digitalFulfillmentService;

    public BuyAgainService(OrderRepository orderRepository,PaymentRepository paymentRepository,DigitalFulfillmentService digitalFulfillmentService) {
        this._orderRepository = orderRepository;
        this._paymentRepository = paymentRepository;
        this._digitalFulfillmentService = digitalFulfillmentService;
    }

    @Transactional
    public CheckoutResponse buyAgain(Long prevOrderId, Long buyerId){
        Order prev = _orderRepository.findByIdWithItems(prevOrderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + prevOrderId ));

        if(!prev.getBuyerId().equals(buyerId)){
            throw new BadRequestException("You can buy again only your oun order");
        }

        Order cloned = prev.copyAsDraft();
        _orderRepository.save(cloned);

        Payment payment = new Payment(
                cloned.getId(),
                "MOCK_PROVIDER",
                Payment.PaymentStatus.SUCCESS,
                cloned.getTotalAmount()
        );
        _paymentRepository.save(payment);

        cloned.markPaid();

        List<CheckoutResponse.DigitalKeyIssued> issueds = new ArrayList<>();
        for (OrderItem item : cloned.getItems()) {
            if (item.getKind() == OrderItem.ItemKind.DIGITAL){
                List<DigitalKey> keys = _digitalFulfillmentService.issueKeysForItem(item);
                for (DigitalKey key : keys) {
                    issueds.add(new CheckoutResponse.DigitalKeyIssued(item.getProductId(),key.getKeyValue()));
                }

            }
        }
        CheckoutResponse resp = new CheckoutResponse();
        resp.orderId = cloned.getId();
        resp.paymentStatus = "PAID";
        resp.totalAmount = cloned.getTotalAmount();
        resp.issuedKeys = issueds;

        return resp;
    }
}
