package com.backend.core.infrastructure.business.websocket;

import com.backend.core.entity.websocket.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessageSendingOperations messagingOperation;


    @MessageMapping("/postComment/{productId}/{color}")
    public void postComment(@Payload WebSocketMessage chatWebSocketMessage,
                            @DestinationVariable String productId,
                            @DestinationVariable String color) {
        messagingOperation.convertAndSend("/comment/" + productId + "/" + color, chatWebSocketMessage);
    }

    @MessageMapping("/addUser/{productId}/{color}")
    public void addUser(@Payload WebSocketMessage chatWebSocketMessage,
                        @DestinationVariable String productId,
                        @DestinationVariable String color,
                        SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatWebSocketMessage.getSender());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("productId", productId);
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("productColor", color);

        messagingOperation.convertAndSend("/comment/" + productId + "/" + color, chatWebSocketMessage);
    }

    @MessageMapping("/typingComment/{productId}/{color}")
    public void typingDetect(@Payload WebSocketMessage chatWebSocketMessage,
                             @DestinationVariable String productId,
                             @DestinationVariable String color) {
        messagingOperation.convertAndSend("/comment/" + productId + "/" + color, chatWebSocketMessage);
    }

    @MessageMapping("/likeComment/{productId}/{color}/{customerId}/{commentId}")
    public void likeComment(@Payload WebSocketMessage chatWebSocketMessage,
                            @DestinationVariable String productId,
                            @DestinationVariable String color,
                            @DestinationVariable String customerId,
                            @DestinationVariable String commentId) {
        messagingOperation.convertAndSend("/comment/" + productId + "/" + color + "/" + customerId + "/" + commentId, chatWebSocketMessage);
    }
}
