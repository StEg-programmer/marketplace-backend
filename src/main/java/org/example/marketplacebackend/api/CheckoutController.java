package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.domain.order.Order;
import org.example.marketplacebackend.dto.CheckoutRequest;
import org.example.marketplacebackend.dto.CheckoutResponse;
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

    private final CheckoutService _checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this._checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout (@Valid @RequestBody CheckoutRequest req) {
        return ResponseEntity.ok(_checkoutService.checkout(req));
    }
}
