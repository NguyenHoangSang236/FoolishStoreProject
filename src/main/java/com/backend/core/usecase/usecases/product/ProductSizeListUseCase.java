package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSizeListUseCase extends UseCase<ProductSizeListUseCase.InputValue, ApiResponse> {
    @Autowired
    CatalogRepository catalogRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;

    @Override
    public ApiResponse execute(InputValue input) {
        int id = input.getProductId();
        String color = input.getColor();

        // get size list of product
        List<String> sizeList = productManagementRepo.getSizeListByProductIdAndColor(id, color);

        if (!sizeList.isEmpty()) {
            return new ApiResponse("success", sizeList, HttpStatus.OK);
        } else {
            return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        String color;
        int productId;
    }
}
