package com.backend.core.usecase.business.notification;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.notification.model.Notification;
import com.backend.core.infrastructure.business.notification.repository.NotificationRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SeenNotificationUseCase extends UseCase<SeenNotificationUseCase.InputValue, ApiResponse> {
    @Autowired
    NotificationRepository notificationRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        Notification notification = notificationRepo.getNotificationById(input.getNotificationId());
        Account account = valueRenderUtils.getCurrentAccountFromRequest(input.getHttpRequest());

        // check if this notification is existed
        if (notification == null) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        }

        String topic = notification.getTopic();

        // check if this notification belongs to this account or not
        if ((!account.getUsername().equals(topic) && topic != "admin") || (!account.getRole().equals(RoleEnum.ADMIN) && topic == "admin")) {
            return new ApiResponse("failed", "This notification does not belong to you", HttpStatus.BAD_REQUEST);
        }

        notification.setSeen(true);
        notificationRepo.save(notification);

        return new ApiResponse("success", "Seen notification successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        int notificationId;
        HttpServletRequest httpRequest;
    }
}
