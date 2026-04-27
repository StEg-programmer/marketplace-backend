package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.domain.catalog.ProductStock;
import org.example.marketplacebackend.dto.UpsertProductStockRequest;
import org.example.marketplacebackend.service.ProductStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/seller/products/{productId}/stock")
public class SellerProductStockController {

    private final ProductStockService _productStockService;

    public SellerProductStockController(ProductStockService productStockService) {
        this._productStockService = productStockService;
    }

    @PutMapping
    public ResponseEntity<?> upsertStock(@PathVariable Long productId,
                                         @Valid @RequestBody UpsertProductStockRequest req) {

        ProductStock stock = _productStockService.upsertStock(productId, req);

        return ResponseEntity.ok(Map.of(
                "productId",stock.getProductId(),
                "availableQuantity",stock.getAvailableQuantity(),
                "reservedQuantity",stock.getReservedQuantity()
        ));
    }

    @GetMapping
    public ResponseEntity<?> getStock(@PathVariable Long productId) {
        ProductStock stock = _productStockService.getStock(productId);

        return ResponseEntity.ok(Map.of(
                "productId",stock.getProductId(),
                "availableQuanity",stock.getAvailableQuantity(),
                "reservedQuantity",stock.getReservedQuantity()
        ));

    }
}
