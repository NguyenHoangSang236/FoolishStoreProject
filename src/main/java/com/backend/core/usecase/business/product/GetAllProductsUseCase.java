package com.backend.core.usecase.business.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllProductsUseCase extends UseCase<GetAllProductsUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        PaginationDTO pagination = input.getPagination();

        List<ProductRenderInfoDTO> productRenderList = productRenderInfoRepo.getAllProducts(
                valueRenderUtils.getStartLineForQueryPagination(pagination.getLimit(), pagination.getPage()),
                pagination.getLimit()
        );
        return new ApiResponse("success", productRenderList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        PaginationDTO pagination;
    }
}
