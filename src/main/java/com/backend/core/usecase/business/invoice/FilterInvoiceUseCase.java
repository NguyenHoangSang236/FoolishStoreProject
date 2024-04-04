package com.backend.core.usecase.business.invoice;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.invoice.gateway.InvoiceFilterRequestDTO;
import com.backend.core.infrastructure.business.invoice.dto.InvoiceRenderInfoDTO;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.QueryService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.FilterTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterInvoiceUseCase extends UseCase<FilterInvoiceUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    QueryService queryService;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        try {
            InvoiceFilterRequestDTO invoiceFilterRequest = input.getInvoiceFilterRequest();
            HttpServletRequest request = input.getHttpRequest();

            boolean isCustomer = valueRenderUtils.getCurrentAccountFromRequest(request).getRole().equals(RoleEnum.CUSTOMER.name());

            String filterQuery = queryService.getFilterQuery(invoiceFilterRequest, FilterTypeEnum.INVOICE, request, isCustomer);

            // get list from query
            List<InvoiceRenderInfoDTO> invoiceRenderList = customQueryRepo.getBindingFilteredList(filterQuery, InvoiceRenderInfoDTO.class);

            return new ApiResponse("success", invoiceRenderList, HttpStatus.OK);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        InvoiceFilterRequestDTO invoiceFilterRequest;
        HttpServletRequest httpRequest;
    }
}
