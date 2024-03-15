package com.backend.core.usecase.usecases.account;

import com.backend.core.entity.account.gateway.AccountFilterDTO;
import com.backend.core.entity.account.gateway.AccountFilterRequestDTO;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.business.account.dto.StaffRenderInfoDTO;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FilterAccountUseCase extends UseCase<FilterAccountUseCase.Input, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CustomQueryRepository customQueryRepo;

    @Override
    public ApiResponse execute(Input input) {
        try {
            AccountFilterRequestDTO accountFilterRequest = input.getAccountFilterRequest();
            String filterQuery = valueRenderUtils.accountFilterQuery(
                    (AccountFilterDTO) accountFilterRequest.getFilter(),
                    accountFilterRequest.getPagination()
            );

            if (accountFilterRequest.getPagination().getType().equals(RoleEnum.ADMIN.name())) {
                List<StaffRenderInfoDTO> staffInfoList = customQueryRepo.getBindingFilteredList(filterQuery, StaffRenderInfoDTO.class);

                return new ApiResponse("success", staffInfoList, HttpStatus.OK);
            } else if (accountFilterRequest.getPagination().getType().equals(RoleEnum.CUSTOMER.name())) {
                List<CustomerRenderInfoDTO> customerInfoList = customQueryRepo.getBindingFilteredList(filterQuery, CustomerRenderInfoDTO.class);

                return new ApiResponse("success", customerInfoList, HttpStatus.OK);
            } else
                return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.toString());
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class Input implements InputValues {
        AccountFilterRequestDTO accountFilterRequest;
    }
}
