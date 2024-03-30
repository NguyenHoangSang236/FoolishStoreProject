package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.GhnUtils;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GetGhnAvailableServiceListUseCase extends UseCase<GetGhnAvailableServiceListUseCase.InputValue, ApiResponse> {
    @Autowired
    GhnUtils ghnUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        AddressCodeDTO addressCode = input.getAddressCodeRequest();

        // get service map list from ids of districts from GHN api
        List<Map> serviceMapList = ghnUtils.getAvailableServiceList(addressCode.getFromDistrictId(), addressCode.getToDistrictId());

        if (serviceMapList != null && !serviceMapList.isEmpty()) {
            return new ApiResponse("success", serviceMapList, HttpStatus.OK);
        } else {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.NO_CONTENT);
        }
    }

    @Value
    public static class InputValue implements UseCase.InputValues {
        AddressCodeDTO addressCodeRequest;
    }
}
