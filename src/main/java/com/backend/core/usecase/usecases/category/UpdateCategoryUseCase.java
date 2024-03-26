package com.backend.core.usecase.usecases.category;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UpdateCategoryUseCase extends UseCase<UpdateCategoryUseCase.InputValue, ApiResponse> {
    @Autowired
    CatalogRepository categoryRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        Catalog updatingCatalog = input.getCategory();

        if (updatingCatalog.getName() == null ||
                updatingCatalog.getName().isBlank() ||
                updatingCatalog.getImage() == null ||
                updatingCatalog.getImage().isBlank() ||
                updatingCatalog.getId() == 0) {
            return new ApiResponse("failed", "Updating category must have id and an image or name", HttpStatus.BAD_REQUEST);
        }

        Catalog existedCatalog = categoryRepo.getCatalogById(updatingCatalog.getId());

        if (existedCatalog == null) {
            return new ApiResponse("failed", "This category does not exist", HttpStatus.BAD_REQUEST);
        }

        String firstWordCapitalCatalogName = valueRenderUtils.capitalizeFirstLetterOfEachWord(updatingCatalog.getName());
        Set<String> catalogNames = categoryRepo.getAllCatalogName();

        if (catalogNames.contains(firstWordCapitalCatalogName)) {
            return new ApiResponse("failed", "This category name has already existed", HttpStatus.BAD_REQUEST);
        }

        updatingCatalog.setName(firstWordCapitalCatalogName);
        categoryRepo.save(updatingCatalog);

        return new ApiResponse("success", "Update category successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        Catalog category;
    }
}
