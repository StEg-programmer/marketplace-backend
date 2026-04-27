package org.example.marketplacebackend.service.order;

import org.example.marketplacebackend.api.exception.NotFoundException;
import org.example.marketplacebackend.domain.digital.DigitalKey;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.example.marketplacebackend.dto.OrderDetailsResponse;
import org.example.marketplacebackend.repository.DigitalKeyRepository;
import org.example.marketplacebackend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RealOrderDetailsService implements OrderDetailsUseCase {

    private final OrderRepository orderRepository;
    private final DigitalKeyRepository digitalKeyRepository;

    public RealOrderDetailsService(OrderRepository orderRepository,
                                   DigitalKeyRepository digitalKeyRepository) {
        this.orderRepository = orderRepository;
        this.digitalKeyRepository = digitalKeyRepository;
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId, Long requesterBuyerId) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));

        OrderDetailsResponse response = new OrderDetailsResponse();

        response.orderId = order.getId();
        response.buyerId = order.getBuyerId();
        response.status = order.getStatus().name();
        response.paymentStatus = order.getPaymentStatus().name();
        response.paymentType = order.getPaymentType();
        response.deliveryType = order.getDeliveryType();
        response.totalAmount = order.getTotalAmount();
        response.createdAt = order.getCreatedAt();

        for (OrderItem item : order.getItems()) {
            OrderDetailsResponse.DetatilsItem dto = new OrderDetailsResponse.DetatilsItem();

            dto.orderItemId = item.getId();
            dto.productId = item.getProductId();
            dto.title = item.getTitleSnapshot();
            dto.kind = item.getKind().name();

            dto.quantity = item.getQuantity();
            dto.unitPrice = item.getPriceSnapshot();
            dto.lineTotal = item.getPriceSnapshot()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            if (item.getKind() == OrderItem.ItemKind.DIGITAL) {
                fillDigitalInfo(order, item, dto);
            } else if (item.getKind() == OrderItem.ItemKind.PHYSICAL) {
                fillPhysicalInfo(order, dto);
            }

            response.items.add(dto);
        }

        return response;
    }

    private void fillDigitalInfo(Order order,
                                 OrderItem item,
                                 OrderDetailsResponse.DetatilsItem dto) {
        dto.fulfillmentType = "DIGITAL_KEY";

        if (order.getPaymentStatus() == Order.PaymentStatus.PAID) {
            List<DigitalKey> keys = digitalKeyRepository.findAllByOrderItemId(item.getId());

            dto.digitalKeys = keys.stream()
                    .map(DigitalKey::getKeyValue)
                    .toList();

            dto.fulfillmentStatus = "ISSUED";
            dto.fulfillmentMessage = "Digital keys were issued after payment";
        } else {
            dto.fulfillmentStatus = "WAITING_PAYMENT";
            dto.fulfillmentMessage = "Digital keys will be available after payment";
        }
    }

    private void fillPhysicalInfo(Order order,
                                  OrderDetailsResponse.DetatilsItem dto) {
        dto.fulfillmentType = "PHYSICAL_DELIVERY";

        if (order.getPaymentStatus() == Order.PaymentStatus.PAID) {
            dto.fulfillmentStatus = "ACCEPTED";
            dto.fulfillmentMessage = "Physical product order accepted. Delivery type: "
                    + (order.getDeliveryType() == null ? "NONE" : order.getDeliveryType());
        } else {
            dto.fulfillmentStatus = "WAITING_PAYMENT";
            dto.fulfillmentMessage = "Physical product will be processed after payment";
        }
    }
}