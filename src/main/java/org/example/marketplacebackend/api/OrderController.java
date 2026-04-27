package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.dto.BuyAgainRequest;
import org.example.marketplacebackend.dto.OrderDetailsResponse;
import org.example.marketplacebackend.service.BuyAgainService;
import org.example.marketplacebackend.service.CheckoutFacade;
import org.example.marketplacebackend.service.order.OrderDetailsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final CheckoutFacade _checkoutFacade;
    private final OrderDetailsUseCase _orderDetailsUseCase;


    public OrderController(CheckoutFacade checkoutFacade,
                           OrderDetailsUseCase orderDetailsUseCase) {
        this._checkoutFacade = checkoutFacade;
        this._orderDetailsUseCase = orderDetailsUseCase;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable("orderId") Long orderId,
                                                                @RequestParam Long buyerId) {
        return ResponseEntity.ok(_orderDetailsUseCase.getOrderDetails(orderId, buyerId));
    }


    @PostMapping("/{orderId}/buy-again")
    public ResponseEntity<?> buyAgain(@PathVariable Long orderId,
                                      @Valid @RequestBody BuyAgainRequest req) {
        return ResponseEntity.ok(_checkoutFacade.buyAgain(orderId,req.buyerId));
    }
}
