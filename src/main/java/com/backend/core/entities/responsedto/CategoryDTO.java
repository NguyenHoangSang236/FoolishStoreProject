package com.backend.core.entities.responsedto;

import com.backend.core.entities.tableentity.Catalog;
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

    public void getDataFromCatalog(Catalog catalog) {
        this.id = catalog.getId();
        this.name = catalog.getName();
        this.image = catalog.getImage();
    }

    public static List<CategoryDTO> getListFromCatalogList(List<Catalog> catalogList) {
        List<CategoryDTO> resList = new ArrayList<>();

        for(Catalog catalog : catalogList) {
            CategoryDTO category = new CategoryDTO();
            category.getDataFromCatalog(catalog);

            resList.add(category);
        }

        return resList;
    }
}
