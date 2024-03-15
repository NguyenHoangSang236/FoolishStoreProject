package com.backend.core.infrastructure.config.api;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.usecase.TestUseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapper {
    public static ResponseEntity<ApiResponse> map(ApiResponse response) {
        HttpStatus status = response.getStatus();

        ApiResponse apiRes = ApiResponse.builder()
                .result(response.getResult())
                .content(response.getContent())
                .build();

        switch (status) {
            case OK -> ResponseEntity.ok(apiRes);
            case INTERNAL_SERVER_ERROR -> ResponseEntity.internalServerError().body(apiRes);
            case BAD_REQUEST -> ResponseEntity.badRequest().body(apiRes);
            case UNAUTHORIZED, NO_CONTENT -> new ResponseEntity<>(apiRes, status);
        }

        return null;
    }
}
