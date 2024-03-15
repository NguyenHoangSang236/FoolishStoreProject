package com.backend.core.entity.api;

import com.backend.core.usecase.UseCase;
import com.google.api.Http;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse implements UseCase.OutputValues {
    private String result;
    private Object content;
    private String message;
    private HttpStatus status;

    public ApiResponse(String result, Object content, HttpStatus status) {
        this.result = result;
        this.content = content;
        this.status = status;
    }

    public ApiResponse(String result, Object content) {
        this.result = result;
        this.content = content;
    }
}
