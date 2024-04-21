package com.backend.core.entity.product.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductFilterDTO implements Serializable {
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
