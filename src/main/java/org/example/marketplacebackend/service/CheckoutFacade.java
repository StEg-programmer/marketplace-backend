package org.example.marketplacebackend.service;


import org.example.marketplacebackend.dto.CheckoutRequest;
import org.example.marketplacebackend.dto.CheckoutResponse;
import org.springframework.stereotype.Service;

@Service
public class CheckoutFacade {

    private final CheckoutService checkoutService;
    private final BuyAgainService buyAgainService;

    public CheckoutFacade(CheckoutService checkoutService,
                          BuyAgainService buyAgainService) {
        this.checkoutService = checkoutService;
        this.buyAgainService = buyAgainService;
    }

    public CheckoutResponse placeOrder(CheckoutRequest request) {
        return checkoutService.checkout(request);
    }

    public CheckoutResponse buyAgain(Long orderId, Long buyerId) {
        return buyAgainService.buyAgain(orderId,buyerId);
    }
}
