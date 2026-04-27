package org.example.marketplacebackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpsertProductStockRequest {
    @NotNull
    public Long sellerId;

    @Min(0)
    public int availableQuantity;
}
