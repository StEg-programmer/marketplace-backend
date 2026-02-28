package org.example.marketplacebackend.api;

import jakarta.validation.Valid;
import org.example.marketplacebackend.dto.AddDigitalKeysRequest;
import org.example.marketplacebackend.service.DigitalKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/seller/digital-keys")
public class DigitalKeyController {

    private final DigitalKeyService _digitalKeyService;

    public DigitalKeyController(DigitalKeyService _digitalKeyService) {
        this._digitalKeyService = _digitalKeyService;
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AddDigitalKeysRequest req){
        int added = _digitalKeyService.addKeys(req);
        return ResponseEntity.ok(Map.of("added",added));
    }

    @GetMapping("/available/{productId}")
    public ResponseEntity<?> coun(@PathVariable Long productId){
        return ResponseEntity.ok(Map.of("available",_digitalKeyService.availableKeyCount(productId)));
    }
}
