package com.backend.core.usecase.usecases.category;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterCategoryUseCase extends UseCase<FilterCategoryUseCase.InputValue, ApiResponse> {
    @Autowired
    CatalogRepository categoryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        try {
            List<Catalog> categoryList = categoryRepo.getAllCatalogs();
            return new ApiResponse("success", categoryList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {

    }
}
