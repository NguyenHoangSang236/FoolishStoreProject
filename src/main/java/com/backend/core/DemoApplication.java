package com.backend.core;

import com.backend.core.infrastructure.config.api.GlobalExceptionHandler;
import com.backend.core.infrastructure.config.api.SourceCodeHandlerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.Collections;


@SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class},
        scanBasePackages = {
                "com.backend.core.usecase",
                "com.backend.core.entity",
                "com.backend.core.infrastructure",
                "com.backend.core",
        }
)
@Import(GlobalExceptionHandler.class)
public class DemoApplication {
    public static void main(String[] args) throws IOException {
        SourceCodeHandlerUtil.deleteDirectory("tokens");

        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }
}
