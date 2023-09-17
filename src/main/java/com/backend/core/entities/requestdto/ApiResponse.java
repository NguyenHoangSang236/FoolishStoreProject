package com.backend.core.entities.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private String result;
    private Object content;
    private String message;

    public ApiResponse(String result, Object content) {
        this.result = result;
        this.content = content;
    }

    public ApiResponse(String result, Object content, String message) {
        this.result = result;
        this.content = content;
        this.message = message;
    }
}
