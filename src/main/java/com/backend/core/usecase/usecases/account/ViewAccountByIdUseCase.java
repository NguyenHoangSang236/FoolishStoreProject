package com.backend.core.usecase.usecases.account;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.business.account.dto.StaffRenderInfoDTO;
import com.backend.core.infrastructure.business.account.repository.CustomerRenderInfoRepository;
import com.backend.core.infrastructure.business.account.repository.StaffRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ViewAccountByIdUseCase extends UseCase<ViewAccountByIdUseCase.Input, ApiResponse> {
    @Autowired
    CustomerRenderInfoRepository customerRenderInfoRepo;
    @Autowired
    StaffRenderInfoRepository staffRenderInfoRepo;

    @Override
    public ApiResponse execute(Input input) {
        try {
            int id = input.getId();

            if (id < 1) {
                return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
            }

            CustomerRenderInfoDTO customerRenderInfo = customerRenderInfoRepo.getCustomerInfoByAccountId(id);
            StaffRenderInfoDTO staffRenderInfo = staffRenderInfoRepo.getStaffInfoByAccountId(id);

            if (customerRenderInfo != null) {
                return new ApiResponse("success", customerRenderInfo, HttpStatus.OK);
            } else if (staffRenderInfo != null) {
                return new ApiResponse("success", staffRenderInfo, HttpStatus.OK);
            } else
                return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.toString());
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class Input implements UseCase.InputValues{
        int id;
    }
}
