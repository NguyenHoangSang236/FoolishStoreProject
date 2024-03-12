package com.backend.core.usecase.service;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public ResponseEntity<ApiResponse> sendMessage(NotificationDTO notification, HttpServletRequest request);

    public ResponseEntity<ApiResponse> addNewDeviceFcmToken(String token, HttpServletRequest request);

    public ResponseEntity<ApiResponse> subscribeToTopic(NotificationDTO notification, HttpServletRequest request);

    public ResponseEntity<ApiResponse> unsubscribeFromTopic(NotificationDTO notification, HttpServletRequest request);

    public ResponseEntity<ApiResponse> getNotificationList(Object object, HttpServletRequest request);
}
