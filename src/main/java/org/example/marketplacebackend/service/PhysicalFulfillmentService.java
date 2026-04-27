package org.example.marketplacebackend.service;

import org.example.marketplacebackend.domain.catalog.ProductStock;
import org.example.marketplacebackend.domain.order.OrderItem;
import org.example.marketplacebackend.repository.ProductStockRepository;
import org.example.marketplacebackend.api.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhysicalFulfillmentService {

    private final ProductStockRepository productStockRepository;

    public PhysicalFulfillmentService(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Transactional
    public void fulfill(OrderItem item) {
        ProductStock stock = productStockRepository.findByProductId(item.getProductId())
                .orElseThrow(() -> new BadRequestException("Stock not found for productId=" + item.getProductId()));

        if (!stock.hasEnough(item.getQuantity())) {
            throw new BadRequestException("Not enough stock for productId=" + item.getProductId());
        }

        stock.decreaseAvailable(item.getQuantity());
        productStockRepository.save(stock);
    }
}