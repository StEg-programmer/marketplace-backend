package org.example.marketplacebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AddDigitalKeysRequest {

    @NotNull
    public Long sellerId;

    @NotNull
    public Long productId;

    @NotEmpty
    public List<@NotBlank String> keys;
}
