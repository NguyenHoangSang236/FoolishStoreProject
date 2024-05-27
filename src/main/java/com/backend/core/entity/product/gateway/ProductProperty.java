package com.backend.core.entity.product.gateway;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductProperty implements Serializable {
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("importDate")
    Date importDate;
}
