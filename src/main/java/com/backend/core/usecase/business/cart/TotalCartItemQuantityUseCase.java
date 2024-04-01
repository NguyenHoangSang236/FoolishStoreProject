package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TotalCartItemQuantityUseCase extends UseCase<TotalCartItemQuantityUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CartRepository cartRepo;

    @Override
    public ApiResponse execute(InputValue input) {
        try {
            int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
            int totalQuantity = cartRepo.getCartQuantityByCustomerId(customerId);

            return new ApiResponse("success", totalQuantity, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        HttpServletRequest httpRequest;
    }
}
