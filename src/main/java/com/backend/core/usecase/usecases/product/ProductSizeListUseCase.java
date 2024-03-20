package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.dto.CategoryDTO;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.business.product.dto.AuthenProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSizeListUseCase extends UseCase<ProductSizeListUseCase.InputValue, ApiResponse> {
    @Autowired
    CatalogRepository catalogRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;

    @Override
    public ApiResponse execute(InputValue input) {
        try {
            int id = input.getProductId();
            String color = input.getColor();

            // get size list of product
            List<String> sizeList = productManagementRepo.getSizeListByProductIdAndColor(id, color);

            if (!sizeList.isEmpty()) {
                return new ApiResponse("success", sizeList, HttpStatus.OK);
            } else {
                return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        String color;
        int productId;
    }
}
