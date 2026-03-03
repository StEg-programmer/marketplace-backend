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
    private final OrderAssembler _orderAssembler;
    private final CheckoutProcessor _checkoutProcessor;

    public CheckoutService(ProductRepository productRepository,
                           OrderAssembler orderAssembler,
                           CheckoutProcessor checkoutProcessor) {
        this.productRepository = productRepository;
        this._checkoutProcessor =  checkoutProcessor;
        this._orderAssembler = orderAssembler;
    }

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest req) {

        List<OrderAssembler.ProductQty> items = new  ArrayList<>();

        for (CheckoutRequest.Item item : req.items) {
            Product product = productRepository.findById(item.productId)
                    .orElseThrow(() -> new NotFoundException("Product not found: " + item.productId));
            items.add(new OrderAssembler.ProductQty(product, item.quantity));
        }

        Order order = _orderAssembler.build(req.buyerId,items);
        return _checkoutProcessor.process(order);
    };
}