package com.backend.core.entities.requestdto.product;

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

    @JsonProperty("name")
    String name;


    public ProductFilterDTO(String[] categories, double minPrice, double maxPrice, String brand) {
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.brand = brand;
    }

    public ProductFilterDTO(String name) {
        this.name = name;
    }

    public ProductFilterDTO() {
    }
}
