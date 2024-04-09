package com.backend.core.infrastructure.config.websocket;

import com.backend.core.entity.websocket.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@Slf4j
public class WebSocketEventListener {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        String productId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("productId");
        String productColor = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("productColor");
        if (username != null) {
            log.info("User Disconnected : " + username);

            WebSocketMessage chatWebSocketMessage = new WebSocketMessage();
            chatWebSocketMessage.setType(WebSocketMessage.MessageType.LEAVE);
            chatWebSocketMessage.setSender(username);

            messagingTemplate.convertAndSend("/comment/product_id=" + productId + "&product_color=" + productColor, chatWebSocketMessage);
        }
    }
}