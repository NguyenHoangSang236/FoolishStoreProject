package com.backend.core.entity.product.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductAttribute {
    @NotNull
    @JsonProperty("color")
    String color;

    @NotNull
    @JsonProperty("sizeQuantities")
    List<ProductSizeQuantity> sizeQuantities;

    @NotNull
    @JsonProperty("images")
    String[] images;
}
