package com.backend.core.controller.common;

import com.backend.core.entities.tableentity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @MessageMapping("/sendNotification")
    @SendTo("/unauthen/notification/showAllNotifications")
    public Notification sendMessage(Notification noti) {
        return noti;
    }
}
