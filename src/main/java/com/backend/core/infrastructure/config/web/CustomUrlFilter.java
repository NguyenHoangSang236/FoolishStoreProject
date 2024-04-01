package com.backend.core.infrastructure.config.web;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;


@Slf4j
public class CustomUrlFilter implements Filter {
    private static final String REQUEST_ID = "request_id";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // generate request ID
        String requestId = UUID.randomUUID().toString();
        servletRequest.setAttribute(REQUEST_ID, requestId);

        // filter request and response
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
