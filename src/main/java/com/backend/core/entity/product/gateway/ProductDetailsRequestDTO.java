package com.backend.core.entity.product.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailsRequestDTO implements Serializable {
    @JsonProperty("productId")
    int productId;

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

    @JsonProperty("properties")
    List<ProductProperty> properties;

    @JsonProperty("categoryIds")
    List<Integer> categoryIds;

    @JsonProperty("images")
    List<ProductImage> images;
}
