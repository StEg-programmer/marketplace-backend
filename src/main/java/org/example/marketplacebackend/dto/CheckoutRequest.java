package org.example.marketplacebackend.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class CheckoutRequest {

    @NotNull
    public Long buyerId;

    @NotEmpty
    public List<Item> items;

    public static class Item {
        @NotNull public Long productId;
        @Min(1) public int quantity;
    }
}