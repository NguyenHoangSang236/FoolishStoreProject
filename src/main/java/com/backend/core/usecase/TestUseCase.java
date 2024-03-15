package com.backend.core.usecase;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestUseCase extends UseCase<TestUseCase.InputValues, TestUseCase.OutputValues>{
    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;

    @Override
    public OutputValues execute(InputValues input) {
        return switch (input.getType()) {
            case "NEW_ARRIVAL_PRODUCTS" -> new OutputValues(productRenderInfoRepo.get8NewArrivalProducts(), false);
            case "HOT_DISCOUNT_PRODUCTS" -> new OutputValues(productRenderInfoRepo.get8HotDiscountProducts(), false);
            case "TOP_8_BEST_SELL_PRODUCTS" -> new OutputValues(productRenderInfoRepo.getTop8BestSellProducts(), false);
            default -> new OutputValues(new ArrayList<>(), true);
        };
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        private final String type;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<ProductRenderInfoDTO> productList;
        private final boolean isError;
    }
}
