package com.backend.core.usecase.service;

import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.entity.notification.model.Notification;
import com.backend.core.infrastructure.business.notification.repository.NotificationRepository;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FirebaseService {
    @Autowired
    NotificationRepository notificationRepo;

    public String sendMessage(NotificationDTO notification) {
        try {
            // todo: fix FirebaseApp with name [DEFAULT] doesn't exist bug
            final FirebaseMessaging fcm = FirebaseMessaging.getInstance();

            // build standard firebase payload from DTO
            com.google.firebase.messaging.Notification payload = com.google.firebase.messaging.Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getBody())
                    .build();

            // send notification to multiple devices
            if (notification.getDeviceFcmTokenList() != null) {
                MulticastMessage message = MulticastMessage.builder()
                        .addAllTokens(notification.getDeviceFcmTokenList())
                        .setNotification(payload)
                        .putAllData(notification.getData())
                        .build();

                BatchResponse batchResponse = fcm.sendEachForMulticast(message);

                // check failed message id
                if (batchResponse.getFailureCount() > 0) {
                    List<SendResponse> responses = batchResponse.getResponses();
                    List<String> failedTokens = new ArrayList<>();
                    for (int i = 0; i < responses.size(); i++) {
                        if (!responses.get(i).isSuccessful()) {
                            failedTokens.add(notification.getDeviceFcmTokenList().get(i));
                        }
                    }
                    System.out.println("List of tokens that caused failures: " + failedTokens);
                } else return batchResponse.getResponses().toString();
            }
            // send notification to a topic
            else if (notification.getTopic() != null) {
                Message msg = Message.builder()
                        .setTopic(notification.getTopic())
                        .setNotification(payload)
                        .putAllData(notification.getData())
                        .build();

                String id = fcm.send(msg);

                // save notification to database
                if (id != null && !id.isBlank()) {
                    Notification newNoti = Notification.builder()
                            .topic(notification.getTopic())
                            .title(notification.getTitle())
                            .content(notification.getBody())
                            .additionalData(notification.getData().toString())
                            .notificationDate(new Date())
                            .build();

                    notificationRepo.save(newNoti);
                }

                return id;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
