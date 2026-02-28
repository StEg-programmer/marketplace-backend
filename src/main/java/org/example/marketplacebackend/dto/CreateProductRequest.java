package org.example.marketplacebackend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreateProductRequest {

    @NotNull
    public Long sellerId;

    @NotNull
    public Long categoryId;

    @NotBlank
    @Size(max = 200)
    public String title;

    public String description;

    @NotNull
    public String type;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    public BigDecimal basePrice;

    @Min(0) @Max(90)
    public Integer discountPercent;

    public String attributesJson;

    public Boolean active;
}