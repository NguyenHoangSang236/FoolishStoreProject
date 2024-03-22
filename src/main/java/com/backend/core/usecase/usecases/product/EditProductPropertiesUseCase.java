package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class EditProductPropertiesUseCase extends UseCase<EditProductPropertiesUseCase.InputValue, ApiResponse> {
    @Override
    public ApiResponse execute(InputValue input) {
        return null;
    }

    @Value
    public static class InputValue implements UseCase.InputValues {
        ProductDetailsRequestDTO productDetailsRequest;
    }
}
