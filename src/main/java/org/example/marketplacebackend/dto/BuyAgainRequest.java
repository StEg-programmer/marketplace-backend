package org.example.marketplacebackend.dto;

import jakarta.validation.constraints.NotNull;

public class BuyAgainRequest {
    @NotNull
    public Long buyerId;
}
