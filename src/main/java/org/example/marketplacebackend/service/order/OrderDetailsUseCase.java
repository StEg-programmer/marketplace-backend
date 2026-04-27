package org.example.marketplacebackend.service.order;

import org.example.marketplacebackend.dto.OrderDetailsResponse;

public interface OrderDetailsUseCase {
    OrderDetailsResponse getOrderDetails(Long orderId, Long requesterBuyerId);
}
