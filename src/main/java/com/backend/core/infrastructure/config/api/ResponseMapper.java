package com.backend.core.infrastructure.config.api;

import com.backend.core.entity.api.ApiResponse;
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

        switch (status.name()) {
            case "OK":
                return ResponseEntity.ok(apiRes);
            case "INTERNAL_SERVER_ERROR":
                return ResponseEntity.internalServerError().body(apiRes);
            case "BAD_REQUEST":
                return ResponseEntity.badRequest().body(apiRes);
            case "UNAUTHORIZED, NO_CONTENT":
                return new ResponseEntity<>(apiRes, status);
            default:
                return ResponseEntity.notFound().build();
        }
    }
}
