package com.backend.core.usecase.business.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BestSellerProductsUseCase extends UseCase<BestSellerProductsUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        List<ProductRenderInfoDTO> productList = productRenderInfoRepo.get8NewArrivalProducts();
        return new ApiResponse("success", productList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
    }
}
