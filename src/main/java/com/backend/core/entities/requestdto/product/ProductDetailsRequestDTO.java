package com.backend.core.entities.requestdto.product;

import com.backend.core.entities.tableentity.Catalog;
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

    @JsonProperty("length")
    int length;

    @JsonProperty("width")
    int width;

    @JsonProperty("weight")
    int weight;

    @JsonProperty("height")
    int height;

    @JsonProperty("sellingPrice")
    double sellingPrice;

    @JsonProperty("originalPrice")
    double originalPrice;

    @JsonProperty("discount")
    double discount;

    @JsonProperty("attributes")
    List<ProductAttribute> attributes;

    @JsonProperty("categoryIds")
    List<Integer> categoryIds;
}
