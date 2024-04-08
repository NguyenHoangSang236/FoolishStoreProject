package com.backend.core.infrastructure.config.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Controller
public class MessageController {
    @MessageMapping("/postComment")
    @SendTo("/comment/{productId}/{color}")
    public Message sendMessage(@Payload Message chatMessage, @DestinationVariable String productId, @DestinationVariable String color) {
        return chatMessage;
    }

    @MessageMapping("/addUser")
    @SendTo("/comment/{productId}/{color}")
    public Message addUser(@Payload Message chatMessage, @DestinationVariable String productId, @DestinationVariable String color, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(productId);
        System.out.println(color);

        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("productId", productId);
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("productColor", color);

        return chatMessage;
    }

    @MessageMapping("/typingComment")
    @SendTo("/comment/{productId}/{color}")
    public Message typingDetect(@Payload Message chatMessage, @DestinationVariable String productId, @DestinationVariable String color, SimpMessageHeaderAccessor headerAccessor) {
        return chatMessage;
    }
}
