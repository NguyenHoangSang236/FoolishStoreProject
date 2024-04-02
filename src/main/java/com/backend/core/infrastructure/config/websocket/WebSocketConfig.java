package com.backend.core.infrastructure.config.websocket;

import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getChatWebSocketHandler(), GlobalDefaultStaticVariables.COMMENT_ENDPOINT)
                .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler getChatWebSocketHandler() {
        return new CommentWebSocketHandler();
    }
}

