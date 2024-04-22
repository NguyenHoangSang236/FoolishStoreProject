package com.backend.core.infrastructure.config.web;

import com.backend.core.infrastructure.config.logging.CustomInterceptor;
import com.backend.core.infrastructure.config.logging.CustomUrlFilter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Autowired
            CustomInterceptor customInterceptor;


            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("*")
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "HEAD", "OPTIONS");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(customInterceptor);
            }
        };
    }


//    @Bean
//    public CommonsRequestLoggingFilter logFilter() {
//        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(true);
//        filter.setMaxPayloadLength(10000);
//        filter.setIncludeHeaders(true);
//        filter.setBeforeMessagePrefix("\n------------------------LOGGING REQUEST-----------------------------------\n");
//        filter.setBeforeMessageSuffix("\n\n------------------------END LOGGING REQUEST-----------------------------------\n");
//
//        return filter;
//    }


    @Bean
    public FilterRegistrationBean<CustomUrlFilter> filterRegistrationBean() {
        FilterRegistrationBean<CustomUrlFilter> registrationBean = new FilterRegistrationBean<>();
        CustomUrlFilter customURLFilter = new CustomUrlFilter();

        registrationBean.setFilter(customURLFilter);
        registrationBean.setOrder(2);

        return registrationBean;
    }


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxSessionIdleTimeout(86400000L);

        return container;
    }
}
