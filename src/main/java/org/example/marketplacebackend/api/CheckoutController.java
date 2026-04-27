package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.dto.CheckoutRequest;
import org.example.marketplacebackend.dto.CheckoutResponse;
import org.example.marketplacebackend.service.CheckoutFacade;
import org.example.marketplacebackend.service.CheckoutService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutFacade _checkoutFacade;

    public CheckoutController(CheckoutFacade checkoutFacade) {
        this._checkoutFacade = checkoutFacade;
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout (@Valid @RequestBody CheckoutRequest req) {
        return ResponseEntity.ok(_checkoutFacade.placeOrder(req));
    }
}
