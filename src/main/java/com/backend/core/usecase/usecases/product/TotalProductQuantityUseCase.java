package com.backend.core.usecase.usecases.product;


import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TotalProductQuantityUseCase extends UseCase<TotalProductQuantityUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int totalProductQuantity = productRenderInfoRepo.getTotalProductsQuantity();
        return new ApiResponse("success", totalProductQuantity, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
    }
}
