package org.example.marketplacebackend.service;

import jakarta.transaction.Transactional;
import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.example.marketplacebackend.domain.integration.IntegrationFactory;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryProvider;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryResult;
import org.example.marketplacebackend.domain.integration.delivery.DeliveryType;
import org.example.marketplacebackend.domain.integration.payment.PaymentProvider;
import org.example.marketplacebackend.domain.integration.payment.PaymentResult;
import org.example.marketplacebackend.domain.integration.payment.PaymentType;
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
    private final IntegrationFactory _integrationFactory;
    private final PhysicalFulfillmentService _physicalFulfillmentService;

    public CheckoutProcessor(OrderRepository orderRepository,
                             PaymentRepository paymentRepository,
                             DigitalFulfillmentService digitalFulfillmentService,
                             IntegrationFactory integrationFactory,
                             PhysicalFulfillmentService physicalFulfillmentService) {
        this._orderRepository = orderRepository;
        this._paymentRepository = paymentRepository;
        this._digitalFulfillmentService = digitalFulfillmentService;
        this._integrationFactory = integrationFactory;
        this._physicalFulfillmentService = physicalFulfillmentService;
    }

    @Transactional
    public CheckoutResponse process(Order order) {
        _orderRepository.save(order);

        PaymentType paymentType = order.getPaymentType() != null
                ? PaymentType.valueOf(order.getPaymentType())
                : PaymentType.CARD;

        DeliveryType deliveryType = order.getDeliveryType() != null
                ? DeliveryType.valueOf(order.getDeliveryType())
                : DeliveryType.NONE;

        PaymentProvider paymentProvider = _integrationFactory.paymentFactory().create(paymentType);
        PaymentResult paymentResult = paymentProvider.pay(order.getId(),order.getTotalAmount());

        DeliveryProvider deliveryProvider = _integrationFactory.deliveryFactory().create(deliveryType);
        DeliveryResult deliveryResult = deliveryProvider.arrange(order.getId(),deliveryType);


        Payment payment = new Payment(
                order.getId(),
                paymentResult.providerName(),
                paymentResult.succes() ? Payment.PaymentStatus.SUCCESS : Payment.PaymentStatus.FAILED,
                order.getTotalAmount()
        );

        _paymentRepository.save(payment);

        if(!paymentResult.succes()) {
            CheckoutResponse resp = new CheckoutResponse();
            resp.orderId = order.getId();
            resp.totalAmount =  order.getTotalAmount();
            resp.paymentStatus = "FAILED";
            resp.issuedKeys = List.of();
            return resp;
        }

        order.markPaid();

        List<CheckoutResponse.DigitalKeyIssued> issueds = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            if (item.getKind() == OrderItem.ItemKind.DIGITAL){
                List<DigitalKey> keys = _digitalFulfillmentService.issueKeysForItem(item);
                for (DigitalKey key : keys) {
                    issueds.add(new CheckoutResponse.DigitalKeyIssued(item.getProductId(),key.getKeyValue()));
                }
            }else if (item.getKind() == OrderItem.ItemKind.PHYSICAL){
                _physicalFulfillmentService.fulfill(item);
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
