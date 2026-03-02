package org.example.marketplacebackend.service;

import org.example.marketplacebackend.api.exception.NotFoundException;
import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.example.marketplacebackend.domain.order.Payment;
import org.example.marketplacebackend.dto.CheckoutRequest;
import org.example.marketplacebackend.dto.CheckoutResponse;
import org.example.marketplacebackend.repository.*;
import org.example.marketplacebackend.api.exception.ApiExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderAssembler orderAssembler;
    private final DigitalFulfillmentService _digitalFulfillmentService;

    public CheckoutService(ProductRepository productRepository,
                           OrderRepository orderRepository,
                           PaymentRepository paymentRepository,
                           OrderAssembler orderAssembler,
                           DigitalFulfillmentService digitalFulfillmentService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderAssembler = orderAssembler;
        this._digitalFulfillmentService = digitalFulfillmentService;
    }

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest req) {

        List<OrderAssembler.ProductQty> items = new ArrayList<>();

        for (CheckoutRequest.Item i : req.items) {
            Product product = productRepository.findById(i.productId)
                    .orElseThrow(() -> new NotFoundException("Product not found: " + i.productId));

            items.add(new OrderAssembler.ProductQty(product, i.quantity));
        }

        Order order = orderAssembler.build(req.buyerId, items);
        orderRepository.save(order);

        Payment payment = new Payment(
                order.getId(),
                "MOCK_PROVIDER",
                Payment.PaymentStatus.SUCCESS,
                order.getTotalAmount()
        );
        paymentRepository.save(payment);

        order.markPaid();

        List<CheckoutResponse.DigitalKeyIssued> issued = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            if (item.getKind() == OrderItem.ItemKind.DIGITAL){
                List<DigitalKey> keys = _digitalFulfillmentService.issueKeysForItem(item);
                for (DigitalKey key : keys) {
                    issued.add(new CheckoutResponse.DigitalKeyIssued(item.getProductId(), key.getKeyValue()));
                }
            }
        }

        CheckoutResponse resp = new CheckoutResponse();
        resp.orderId = order.getId();
        resp.totalAmount = order.getTotalAmount();
        resp.paymentStatus = "PAID";
        resp.issuedKeys = issued;

        return resp;
    };
}