package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.cart.model.Cart;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.CartEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RemoveCartItemUseCase extends UseCase<RemoveCartItemUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        int[] selectedCartIdArr = input.getListRequest().getIntegerArray();

        if (selectedCartIdArr != null) {
            for (int id : selectedCartIdArr) {
                Cart cart = cartRepo.getCartById(id);

                if (cart.getCustomer().getId() != customerId) {
                    return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
                }

                if (cart.getBuyingStatus().equals(CartEnum.NOT_BOUGHT_YET.name())) {
                    customQueryRepo.deleteCartById(id);
                } else
                    return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
            }
            return new ApiResponse("success", "Remove successfully", HttpStatus.OK);
        } else
            return new ApiResponse("failed", "Choose items to delete first", HttpStatus.BAD_REQUEST);
    }

    @Value
    public static class InputValue implements InputValues {
        ListRequestDTO listRequest;
        HttpServletRequest httpRequest;
    }
}
