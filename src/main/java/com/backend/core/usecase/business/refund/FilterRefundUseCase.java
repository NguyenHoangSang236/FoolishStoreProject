package com.backend.core.usecase.business.refund;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.refund.gateway.RefundFilterRequestDTO;
import com.backend.core.entity.refund.model.Refund;
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
public class FilterRefundUseCase extends UseCase<FilterRefundUseCase.InputValue, ApiResponse> {
    @Autowired
    QueryService queryService;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        String filterQuery = queryService.getFilterQuery(input.getRefundFilterRequest(), FilterTypeEnum.REFUND, input.getHttpRequest(), false);
        List<Refund> refundList = customQueryRepo.getBindingFilteredList(filterQuery, Refund.class);

        return new ApiResponse("success", refundList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        RefundFilterRequestDTO refundFilterRequest;
        HttpServletRequest httpRequest;
    }
}
