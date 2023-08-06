package com.backend.core;

import com.backend.core.util.SourceCodeHandlerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Collections;


@ComponentScan
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        SourceCodeHandlerUtil.deleteDerectory("/home/mr/JAVA/FoolishStoreProject/tokens");

        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }

}
