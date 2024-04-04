package com.backend.core.usecase.business.notification;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.notification.gateway.NotificationFilterRequestDTO;
import com.backend.core.entity.notification.model.Notification;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.QueryService;
import com.backend.core.usecase.statics.FilterTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterNotificationUseCase extends UseCase<FilterNotificationUseCase.InputValue, ApiResponse> {
    @Autowired
    CustomQueryRepository customQueryRepo;
    @Autowired
    QueryService queryService;


    @Override
    public ApiResponse execute(InputValue input) {
        HttpServletRequest httpRequest = input.getHttpRequest();
        NotificationFilterRequestDTO filterRequest = input.getNotificationFilterRequest();

        String filterQuery = queryService.getFilterQuery(filterRequest, FilterTypeEnum.NOTIFICATION, httpRequest, true);
        List<Notification> notiList = customQueryRepo.getBindingFilteredList(filterQuery, Notification.class);

        return new ApiResponse("success", notiList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements UseCase.InputValues {
        NotificationFilterRequestDTO notificationFilterRequest;
        HttpServletRequest httpRequest;
    }
}
