package com.backend.core.infrastructure.config.logging;

import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import com.backend.core.usecase.service.LoggingService;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@ControllerAdvice
@Slf4j
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    @Autowired
    LoggingService loggingService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter,
                            @NonNull Type type,
                            @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    @NonNull
    public Object afterBodyRead(@NonNull Object body,
                                @NonNull HttpInputMessage inputMessage,
                                @NonNull MethodParameter parameter,
                                @NonNull Type targetType,
                                @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        loggingService.logRequest(httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
