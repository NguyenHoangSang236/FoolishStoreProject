package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotDiscountProductsUseCase extends UseCase<HotDiscountProductsUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;


    @Override
    public ApiResponse execute(HotDiscountProductsUseCase.InputValue input) {
        try {
            List<ProductRenderInfoDTO> productList = productRenderInfoRepo.get8HotDiscountProducts();

            return new ApiResponse("success", productList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {}
}
