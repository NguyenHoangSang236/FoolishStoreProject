package com.backend.core.controller.websocket;

import com.backend.core.entities.tableentity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/sendNotification")
    @SendTo("/authen/notification/filterNotifications")
    public Notification sendMessage(@Payload Notification notification) {
        return notification;
    }
}
