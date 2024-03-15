package com.backend.core.usecase;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TestUseCaseOutputMapper {
    public static ResponseEntity<ApiResponse> map(TestUseCase.OutputValues outputValue) {
        if(outputValue.isError()) {
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(new ApiResponse("success", outputValue.getProductList()), HttpStatus.OK);
        }
    }
}
