package com.backend.core.infrastructure.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class MessageController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @MessageMapping("/postComment/{productId}/{color}")
    public void sendMessage(@Payload Message chatMessage, @DestinationVariable String productId, @DestinationVariable String color) {
        messagingTemplate.convertAndSend("/comment/" + productId + "/" + color, chatMessage);
    }

    @MessageMapping("/addUser/{productId}/{color}")
    public void addUser(@Payload Message chatMessage, @DestinationVariable String productId, @DestinationVariable String color, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(productId);
        System.out.println(color);

        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("productId", productId);
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("productColor", color);

        messagingTemplate.convertAndSend("/comment/" + productId + "/" + color, chatMessage);
    }

    @MessageMapping("/typingComment")
    public Message typingDetect(@Payload Message chatMessage, @DestinationVariable String productId, @DestinationVariable String color, SimpMessageHeaderAccessor headerAccessor) {
        return chatMessage;
    }
}
