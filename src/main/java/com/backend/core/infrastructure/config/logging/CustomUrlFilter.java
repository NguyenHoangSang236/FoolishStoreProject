package com.backend.core.infrastructure.config.logging;

import com.backend.core.infrastructure.config.constants.ConstantValue;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;


@Slf4j
public class CustomUrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // generate request ID
        String requestId = UUID.randomUUID().toString();
        servletRequest.setAttribute(ConstantValue.REQUEST_ID, requestId);

        // filter request and response
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
