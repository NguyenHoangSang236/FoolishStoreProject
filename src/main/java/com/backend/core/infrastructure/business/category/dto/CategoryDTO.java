package com.backend.core.infrastructure.business.category.dto;

import com.backend.core.entity.category.model.Catalog;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    public static List<CategoryDTO> getListFromCatalogList(List<Catalog> catalogList) {
        List<CategoryDTO> resList = new ArrayList<>();

        for (Catalog catalog : catalogList) {
            CategoryDTO category = new CategoryDTO();
            category.getDataFromCatalog(catalog);

            resList.add(category);
        }

        return resList;
    }

    public void getDataFromCatalog(Catalog catalog) {
        this.id = catalog.getId();
        this.name = catalog.getName();
        this.image = catalog.getImage();
    }
}
