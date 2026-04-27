package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.dto.AddDigitalKeysRequest;
import org.example.marketplacebackend.service.DigitalKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/seller/products/{productId}/keys")
public class SellerProductKeyController {

    private final DigitalKeyService digitalKeyService;

    public SellerProductKeyController(DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }

    @PostMapping
    public ResponseEntity<?> addKeys(@PathVariable Long productId,
                                     @Valid @RequestBody AddDigitalKeysRequest req) {
        int added = digitalKeyService.addKeys(productId, req);
        return ResponseEntity.ok(Map.of(
                "productId", productId,
                "added", added
        ));
    }

    @GetMapping("/available-count")
    public ResponseEntity<?> availableCount(@PathVariable Long productId) {
        long count = digitalKeyService.availableKeyCount(productId);
        return ResponseEntity.ok(Map.of(
                "productId", productId,
                "available", count
        ));
    }
}