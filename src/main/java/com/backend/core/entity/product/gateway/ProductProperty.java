package com.backend.core.entity.product.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductProperty {
    @JsonProperty("id")
    int id;

    @NotNull(message = "Please input product's color")
    @JsonProperty("color")
    String color;

    @NotNull(message = "Please input product's size")
    @JsonProperty("size")
    String size;

    @NotNull(message = "Please input available quantity of product")
    @JsonProperty("availableQuantity")
    int availableQuantity;

    @JsonProperty("importDate")
    Date importDate;
}
