package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.domain.catalog.Product;
import org.example.marketplacebackend.dto.CreateProductRequest;
import org.example.marketplacebackend.service.SellerProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/seller/products")
public class SellerProductController {

    private final SellerProductService sellerProductService;

    public SellerProductController(SellerProductService sellerProductService) {
        this.sellerProductService = sellerProductService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> create(
            @Valid @RequestPart("data") CreateProductRequest req,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        return ResponseEntity.ok(sellerProductService.createProduct(req, images));
    }
}