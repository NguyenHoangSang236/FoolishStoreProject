package com.backend.core.serviceImpl.notification;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.notification.NotificationDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.tableentity.DeviceFcmToken;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.firebase.DeviceFcmTokenRepository;
import com.backend.core.service.NotificationService;
import com.backend.core.util.process.FirebaseUtils;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Qualifier("FirebaseMessageNotificationServiceImpl")
public class FirebaseMessageNotificationServiceImpl implements NotificationService {
    @Autowired
    DeviceFcmTokenRepository deviceFcmTokenRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    FirebaseUtils firebaseUtils;


    @Override
    public ResponseEntity<ApiResponse> sendMessage(NotificationDTO notification, HttpServletRequest request) {
        try {
            String id = firebaseUtils.sendMessage(notification);

            if (id != null && !id.isBlank()) {
                return new ResponseEntity<>(new ApiResponse("success", "Sent message successfully"), HttpStatus.OK);
            } else
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
