package com.backend.core.entity.product.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductImage implements Serializable {
    @NotNull
    @JsonProperty("images")
    String[] images;

    @NotNull
    @JsonProperty("color")
    String color;
}
