package com.backend.core.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {
    @JsonProperty("categories")
    String[] categories;

    @JsonProperty("minPrice")
    double minPrice;

    @JsonProperty("maxPrice")
    double maxPrice;

    @JsonProperty("brand")
    String brand;


    public ProductFilterDTO(String[] categories, double minPrice, double maxPrice, String brand) {
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.brand = brand;
    }

    public ProductFilterDTO() {}
}
