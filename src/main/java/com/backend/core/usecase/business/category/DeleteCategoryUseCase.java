package com.backend.core.usecase.business.category;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteCategoryUseCase extends UseCase<DeleteCategoryUseCase.InputValue, ApiResponse> {
    @Autowired
    CatalogRepository categoryRepo;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int id = input.getCategoryId();

        if (id < 1) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        }

        Catalog selectedCatalog = categoryRepo.getCatalogById(id);

        if (selectedCatalog == null) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        }

        customQueryRepo.deleteCatalogsWithProductsById(id);
        categoryRepo.delete(selectedCatalog);

        return new ApiResponse("success", "Remove category successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        int categoryId;
    }
}
