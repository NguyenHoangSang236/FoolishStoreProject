package com.backend.core.entities.requestdto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailsRequestDTO {
    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("brand")
    String brand;

    @JsonProperty("description")
    String description;

    @JsonProperty("sellingPrice")
    double sellingPrice;

    @JsonProperty("originalPrice")
    double originalPrice;

    @JsonProperty("discount")
    double discount;

    @JsonProperty("importDate")
    Date importDate;

    @JsonProperty("attributes")
    List<ProductAttribute> attributes;
}
