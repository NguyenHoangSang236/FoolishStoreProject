package com.backend.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import usecase.util.handler.SourceCodeHandlerUtil;

import java.io.IOException;
import java.util.Collections;


@ComponentScan(basePackages = {
        "../FoolishStoreProject/src/main/java/com/backend/core/entity",
        "../FoolishStoreProject/src/main/java/com/backend/core/infrastructure",
        "../FoolishStoreProject/src/main/java/com/backend/core/service",
        "../FoolishStoreProject/src/main/java/com/backend/core/serviceImpl",
        "../FoolishStoreProject/src/main/java/com/backend/core/usecase",
})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {
    public static void main(String[] args) throws IOException {
        SourceCodeHandlerUtil.deleteDirectory("tokens");

        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }

    @Bean
    public WebMvcConfigurer customCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
