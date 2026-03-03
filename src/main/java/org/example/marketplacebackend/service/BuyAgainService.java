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
    private final CheckoutProcessor _checkoutProcessor;
    public BuyAgainService(OrderRepository orderRepository,CheckoutProcessor checkoutProcessor) {
        this._orderRepository = orderRepository;
        this._checkoutProcessor =  checkoutProcessor;
    }

    @Transactional
    public CheckoutResponse buyAgain(Long prevOrderId, Long buyerId){
        Order prev = _orderRepository.findByIdWithItems(prevOrderId)
                .orElseThrow(()->new NotFoundException("Order not found: "+prevOrderId));

        if(!prev.getBuyerId().equals(buyerId)) {
            throw new BadRequestException("You can buy again only your own order");
        }

        Order cloned = prev.copyAsDraft();

        return _checkoutProcessor.process(cloned);
    }
}
