package com.backend.core.usecase.service;

import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Slf4j
@Service
public class LoggingService {
    private static final String REQUEST_ID = "request_id";
    @Autowired
    ValueRenderUtils valueRenderUtils;

    public void logRequest(HttpServletRequest request, Object body) {
        Object requestId = request.getAttribute(REQUEST_ID);

        StringBuilder data = new StringBuilder();
        data.append("\n\n------------------------LOGGING REQUEST-----------------------------------\n")
                .append("[REQUEST-ID]: ").append(requestId).append("\n")
                .append("[METHOD]: ").append(request.getMethod()).append("\n")
                .append("[PATH]: ").append(request.getRequestURI()).append("\n")
                .append("[QUERIES]: ").append(request.getQueryString()).append("\n")
                .append("[PAYLOAD]: ").append(request.getParameterNames()).append("\n");

        Enumeration<String> payloadNames = request.getHeaderNames();
        while (payloadNames.hasMoreElements()) {
            String key = payloadNames.nextElement();
            String value = request.getHeader(key);
            data.append("---").append(key).append(" : ").append(value).append("\n");
        }

        data.append("[HEADERS]: ").append("\n");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            data.append("---").append(key).append(" : ").append(value).append("\n");
        }
        data.append("------------------------LOGGING REQUEST-----------------------------------\n\n");

        log.info(data.toString());
    }


    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        Object requestId = request.getAttribute(REQUEST_ID);

        String data = "\n\n------------------------LOGGING RESPONSE-----------------------------------\n" +
                "[REQUEST-ID]: " + requestId.toString() + "\n" +
                "[BODY RESPONSE]: " + valueRenderUtils.parseObjectToString(body) +
                "\n------------------------LOGGING RESPONSE-----------------------------------\n";

        log.info(data);
    }
}
