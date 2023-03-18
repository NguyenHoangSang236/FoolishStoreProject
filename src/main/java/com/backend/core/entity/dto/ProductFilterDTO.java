package com.backend.core.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {
    String[] categories;

    double minPrice;

    double maxPrice;

    String brand;


    public ProductFilterDTO(String[] categories, double minPrice, double maxPrice, String brand) {
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.brand = brand;
    }

    public ProductFilterDTO() {}
}
