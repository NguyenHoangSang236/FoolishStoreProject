package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateProductUseCase extends UseCase<RateProductUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductManagementRepository productManagementRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        // convert param object to ProductRenderInfoDTO
        ProductRenderInfoDTO request = input.getProductRenderInfo();

        int productId = request.getProductId();
        int rating = request.getOverallRating();
        String color = request.getColor();

        // rating only from 1-5
        if (rating < 1 || rating > 5) {
            return new ApiResponse("failed", "Rate from one to five stars only", HttpStatus.BAD_REQUEST);
        }

        List<ProductManagement> pmList = productManagementRepo.getProductsManagementListByProductIDAndColor(productId, color);

        if (pmList.isEmpty()) {
            return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
        } else {
            // save rating stars column in each data of product_management table
            for (ProductManagement pm : pmList) {
                // add rating star in column
                pm.addRatingStars(rating);
                // get total rating star
                pm.setTotalRatingNumber();

                productManagementRepo.save(pm);
            }
        }

        return new ApiResponse("success", "Thanks for your rating!", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        ProductRenderInfoDTO productRenderInfo;
    }
}
