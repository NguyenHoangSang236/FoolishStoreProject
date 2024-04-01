package com.backend.core.usecase.business.firebase;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.FirebaseService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SendMessageUseCase extends UseCase<SendMessageUseCase.InputValue, ApiResponse> {
    @Autowired
    FirebaseService firebaseService;


    @Override
    public ApiResponse execute(InputValue input) {
        String id = firebaseService.sendMessage(input.getNotificationRequest());

        if (id != null && !id.isBlank()) {
            return new ApiResponse("success", "Sent message successfully", HttpStatus.OK);
        } else
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Value
    public static class InputValue implements InputValues {
        NotificationDTO notificationRequest;
    }
}
