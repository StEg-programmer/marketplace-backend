package org.example.marketplacebackend.service.order;

import org.example.marketplacebackend.api.exception.BadRequestException;
import org.example.marketplacebackend.api.exception.NotFoundException;
import org.example.marketplacebackend.dto.OrderDetailsResponse;
import org.example.marketplacebackend.repository.OrderRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class OrderDetailsAccesProxy implements OrderDetailsUseCase {

    private final OrderRepository _orderRepository;
    private final RealOrderDetailsService _realSubject;

    public OrderDetailsAccesProxy(OrderRepository orderRepository,
                                  RealOrderDetailsService realSubject) {
        this._orderRepository = orderRepository;
        this._realSubject = realSubject;
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId, Long requestBuyerId) {
        Long ownerButerId = _orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found :" + orderId))
                .getBuyerId();

        if (!ownerButerId.equals(requestBuyerId)) {throw  new BadRequestException("Access denied");}

        return _realSubject.getOrderDetails(orderId, requestBuyerId);
    }
}
