package com.backend.core.serviceImpl.notification;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.notification.NotificationDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.tableentity.DeviceFcmToken;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.firebase.DeviceFcmTokenRepository;
import com.backend.core.service.NotificationService;
import com.backend.core.util.process.ValueRenderUtils;
import com.google.firebase.messaging.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("FirebaseMessageNotificationServiceImpl")
public class FirebaseMessageNotificationServiceImpl implements NotificationService {
    @Autowired
    DeviceFcmTokenRepository deviceFcmTokenRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ResponseEntity<ApiResponse> sendMessage(NotificationDTO notification, HttpServletRequest request) {
        try {
            final FirebaseMessaging fcm = FirebaseMessaging.getInstance();

            // build standard payload from DTO
            Notification payload = Notification.builder()
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
                }

                return new ResponseEntity<>(new ApiResponse("success", "Send a push notification to devices successfully"), HttpStatus.OK);
            }
            // send notification to a topic
            else if (notification.getTopic() != null) {
                Message msg = Message.builder()
                        .setTopic(notification.getTopic())
                        .putAllData(notification.getData())
                        .build();

                String id = fcm.send(msg);
                return new ResponseEntity<>(new ApiResponse("success", "Send a push notification to topic " + notification.getTopic() + " successfully"), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> addNewDeviceFcmToken(String token, HttpServletRequest request) {
        try {
            Account currentAccount = valueRenderUtils.getCurrentAccountFromRequest(request);

            if (currentAccount == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            } else {
                DeviceFcmToken newToken = new DeviceFcmToken();

                newToken.setAccount(currentAccount);
                newToken.setPhoneFcmToken(token);

                deviceFcmTokenRepo.save(newToken);

                return new ResponseEntity<>(new ApiResponse("success", "Add new phone fcm token successfully"), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> subscribeToTopic(NotificationDTO notification, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> unsubscribeFromTopic(NotificationDTO notification, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getNotificationList(Object object, HttpServletRequest request) {
        return null;
    }
}
