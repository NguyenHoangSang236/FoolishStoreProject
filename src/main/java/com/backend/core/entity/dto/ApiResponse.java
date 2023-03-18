package com.backend.core.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String result;
    private Object content;

    public ApiResponse(String result, Object content) {
        this.result = result;
        this.content = content;
    }
}
