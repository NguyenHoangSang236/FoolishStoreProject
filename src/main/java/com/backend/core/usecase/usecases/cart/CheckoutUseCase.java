package com.backend.core.usecase.usecases.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.infrastructure.business.cart.dto.CartCheckoutInfoDTO;
import com.backend.core.infrastructure.business.cart.dto.CartRenderInfoDTO;
import com.backend.core.infrastructure.business.cart.repository.CartRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.util.process.GhnUtils;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckoutUseCase extends UseCase<CheckoutUseCase.InputValue, ApiResponse> {
    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    GhnUtils ghnUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());

        List<CartRenderInfoDTO> selectedCartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

        if (selectedCartItemList == null || selectedCartItemList.isEmpty()) {
            return new ApiResponse("failed", "There is no selected cart item to proceed checkout", HttpStatus.BAD_REQUEST);
        }

        double shippingFee = ghnUtils.calculateShippingFee(input.getCheckoutRequest(), selectedCartItemList);

        double subtotal = 0;

        // calculate subtotal price
        for (CartRenderInfoDTO cartRenderInfoDTO : selectedCartItemList) {
            subtotal += cartRenderInfoDTO.getTotalPrice();
        }

        // init new checkout object
        CartCheckoutInfoDTO checkoutInfo = new CartCheckoutInfoDTO(subtotal, subtotal + shippingFee, shippingFee);

        return new ApiResponse("success", checkoutInfo, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements UseCase.InputValues {
        CartCheckoutDTO checkoutRequest;
        HttpServletRequest httpRequest;
    }
}
