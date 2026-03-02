package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.dto.BuyAgainRequest;
import org.example.marketplacebackend.service.BuyAgainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final BuyAgainService _buyAgainService;

    public OrderController(BuyAgainService buyAgainService) {
        this._buyAgainService = buyAgainService;
    }

    @PostMapping("/{orderId}/buy-again")
    public ResponseEntity<?> buyAgain(@PathVariable Long orderId,
                                      @Valid @RequestBody BuyAgainRequest req) {
        return ResponseEntity.ok(_buyAgainService.buyAgain(orderId,req.buyerId));
    }
}
