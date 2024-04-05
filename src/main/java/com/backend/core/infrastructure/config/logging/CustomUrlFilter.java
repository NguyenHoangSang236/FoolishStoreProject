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

        // filter request and response
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
