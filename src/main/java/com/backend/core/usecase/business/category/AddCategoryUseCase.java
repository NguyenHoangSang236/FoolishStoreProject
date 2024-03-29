package com.backend.core.usecase.business.category;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AddCategoryUseCase extends UseCase<AddCategoryUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CatalogRepository categoryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        Catalog newCatalog = input.getCategory();

        if ((newCatalog.getName() == null || newCatalog.getName().isBlank()) ||
                (newCatalog.getImage() == null || newCatalog.getImage().isBlank())) {
            return new ApiResponse("failed", "New category must have an image and name", HttpStatus.BAD_REQUEST);
        }

        String firstLetterCapitalCatalogName = valueRenderUtils.capitalizeFirstLetterOfEachWord(newCatalog.getName().toLowerCase());
        Catalog existedCatalog = categoryRepo.getCatalogByName(firstLetterCapitalCatalogName);

        if (existedCatalog != null) {
            return new ApiResponse("failed", "This category has already existed", HttpStatus.BAD_REQUEST);
        }

        newCatalog.setName(firstLetterCapitalCatalogName);
        categoryRepo.save(newCatalog);

        return new ApiResponse("success", "Add new category successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        Catalog category;
    }
}
