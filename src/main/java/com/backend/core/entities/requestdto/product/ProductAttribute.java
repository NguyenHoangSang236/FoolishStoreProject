package com.backend.core.entities.requestdto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductAttribute {
    @NotNull
    @JsonProperty("color")
    String color;

    @NotNull
    @JsonProperty("sizes")
    String[] sizes;

    @NotNull
    @JsonProperty("quantity")
    int[] quantity;

    @NotNull
    @JsonProperty("images")
    String[] images;
}
