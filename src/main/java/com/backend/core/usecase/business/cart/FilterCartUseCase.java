package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.CartItemFilterRequestDTO;
import com.backend.core.infrastructure.business.cart.dto.CartRenderInfoDTO;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.QueryService;
import com.backend.core.usecase.statics.FilterTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterCartUseCase extends UseCase<FilterCartUseCase.InputValue, ApiResponse> {
    @Autowired
    QueryService queryService;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        CartItemFilterRequestDTO cartItemFilterRequest = input.getCartItemFilterRequest();
        HttpServletRequest httpRequest = input.getHttpRequest();

        String filterQuery = queryService.getFilterQuery(cartItemFilterRequest, FilterTypeEnum.CART_ITEMS, httpRequest, true);

        List<CartRenderInfoDTO> cartItemList = customQueryRepo.getBindingFilteredList(filterQuery, CartRenderInfoDTO.class);

        return new ApiResponse("success", cartItemList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        CartItemFilterRequestDTO cartItemFilterRequest;
        HttpServletRequest httpRequest;
    }
}
