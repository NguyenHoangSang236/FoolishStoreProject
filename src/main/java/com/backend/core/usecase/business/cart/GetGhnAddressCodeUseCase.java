package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.AddressNameDTO;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.GhnService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GetGhnAddressCodeUseCase extends UseCase<GetGhnAddressCodeUseCase.InputValue, ApiResponse> {
    @Autowired
    GhnService ghnService;


    @Override
    public ApiResponse execute(InputValue input) {
        // get address code using GHN apis
        AddressCodeDTO addressCode = ghnService.getGhnAddressCode(input.getAddressNameRequest());

        if (addressCode != null) {
            return new ApiResponse("success", addressCode, HttpStatus.OK);
        } else {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.NO_CONTENT);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        AddressNameDTO addressNameRequest;
    }
}
