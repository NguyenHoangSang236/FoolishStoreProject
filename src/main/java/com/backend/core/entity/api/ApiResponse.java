package com.backend.core.entity.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String result;
    private Object content;
    private String message;

    public ApiResponse(String result, Object content) {
        this.result = result;
        this.content = content;
    }
}
