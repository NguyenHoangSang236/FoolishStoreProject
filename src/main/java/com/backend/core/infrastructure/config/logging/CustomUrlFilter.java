package com.backend.core.infrastructure.config.logging;

import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
public class CustomUrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // generate request ID
        String requestId = UUID.randomUUID().toString();
        servletRequest.setAttribute(GlobalDefaultStaticVariables.REQUEST_ID, requestId);

//        logRequest((HttpServletRequest) servletRequest);

        // filter request and response
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }


    private void logRequest(HttpServletRequest request) throws IOException {
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
                .append("[PAYLOAD]: ").append(new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8)).append("\n");

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
    }


    private String getRequestBody(BufferedReader  reader) {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
