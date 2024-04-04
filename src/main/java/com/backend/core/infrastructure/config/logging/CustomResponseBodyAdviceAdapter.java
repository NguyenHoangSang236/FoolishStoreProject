package com.backend.core.infrastructure.config.logging;

import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
            logResponse(
                    ((ServletServerHttpRequest) request).getServletRequest(),
                    ((ServletServerHttpResponse) response).getServletResponse(),
                    body
            );
        }

        return body;
    }

    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        Object requestId = request.getAttribute(GlobalDefaultStaticVariables.REQUEST_ID);

        String data = "\n\n------------------------LOGGING RESPONSE-----------------------------------\n" +
                "[REQUEST-ID]: " + requestId.toString() + "\n" +
                "[BODY RESPONSE]: " + valueRenderUtils.parseObjectToString(body) +
                "\n------------------------END LOGGING RESPONSE-----------------------------------\n";

        log.info(data);
    }
}
