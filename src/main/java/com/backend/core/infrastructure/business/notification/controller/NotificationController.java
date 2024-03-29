package com.backend.core.infrastructure.business.notification.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.notification.gateway.NotificationFilterRequestDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.business.notification.FilterNotificationUseCase;
import com.backend.core.usecase.business.notification.SeenNotificationUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/authen/notification", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class NotificationController {
    final UseCaseExecutor useCaseExecutor;
    final FilterNotificationUseCase filterNotificationUseCase;
    final SeenNotificationUseCase seenNotificationUseCase;


    @GetMapping("/seen_notification_id={id}")
    public CompletableFuture<ResponseEntity<ApiResponse>> seenNotification(@PathVariable("id") int id, HttpServletRequest httpRequest) {
        return useCaseExecutor.execute(
                seenNotificationUseCase,
                new SeenNotificationUseCase.InputValue(id, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/filterNotifications")
    public CompletableFuture<ResponseEntity<ApiResponse>> filterNotifications(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationFilterRequestDTO filterRequest = objectMapper.readValue(json, NotificationFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterNotificationUseCase,
                new FilterNotificationUseCase.InputValue(filterRequest, httpRequest),
                ResponseMapper::map
        );
    }
}
