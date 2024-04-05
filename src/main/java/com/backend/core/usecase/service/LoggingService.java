package com.backend.core.usecase.service;

import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Service
@Slf4j
public class LoggingService {
    @Autowired
    ValueRenderUtils valueRenderUtils;


    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        try {
            Object requestId = request.getAttribute(GlobalDefaultStaticVariables.REQUEST_ID);

            String data = "\n\n------------------------LOGGING RESPONSE-----------------------------------\n" +
                    "[REQUEST-ID]: " + requestId.toString() + "\n" +
                    "[URL]: " + request.getRequestURL() + "\n" +
                    "[BODY RESPONSE]: " + valueRenderUtils.parseObjectToString(body) +
                    "\n------------------------END LOGGING RESPONSE-----------------------------------\n";

            log.info(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void logRequest(HttpServletRequest request, Object body) {
        try {
            Object requestId = request.getAttribute(GlobalDefaultStaticVariables.REQUEST_ID);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy");
            Date requestTime = new Date();

            StringBuilder data = new StringBuilder();
            data.append("\n\n------------------------LOGGING REQUEST-----------------------------------\n")
                    .append("[REQUEST-ID]: ").append(requestId).append("\n")
                    .append("[TIME]: ").append(simpleDateFormat.format(requestTime)).append("\n")
                    .append("[METHOD]: ").append(request.getMethod()).append("\n")
                    .append("[PATH]: ").append(request.getRequestURI()).append("\n")
                    .append("[QUERIES]: ").append(request.getQueryString()).append("\n")
                    .append("[PAYLOAD]: ").append(body.toString()).append("\n");

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
            data.append("------------------------END LOGGING REQUEST-----------------------------------\n\n");

            log.info(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
