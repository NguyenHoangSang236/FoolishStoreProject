package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.gateway.ProductFilterRequestDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.FilterTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilterProductUseCase extends UseCase<FilterProductUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        ProductFilterRequestDTO productFilterRequest = input.getProductFilterRequest();
        HttpServletRequest request = input.getHttpRequest();

        List<ProductRenderInfoDTO> productRenderList = new ArrayList<>();

        try {
            String filterQuery = valueRenderUtils.getFilterQuery(productFilterRequest, FilterTypeEnum.PRODUCT, request, false);
            productRenderList = customQueryRepo.getBindingFilteredList(filterQuery, ProductRenderInfoDTO.class);

            return new ApiResponse("success", productRenderList, HttpStatus.OK);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        ProductFilterRequestDTO productFilterRequest;
        HttpServletRequest httpRequest;
    }
}
