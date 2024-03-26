package com.backend.core.usecase.usecases.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.AddressNameDTO;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.GhnUtils;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GetGhnAddressCodeUseCase extends UseCase<GetGhnAddressCodeUseCase.InputValue, ApiResponse> {
    @Autowired
    GhnUtils ghnUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        // get address code using GHN apis
        AddressCodeDTO addressCode = ghnUtils.getGhnAddressCode(input.getAddressNameRequest());

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
