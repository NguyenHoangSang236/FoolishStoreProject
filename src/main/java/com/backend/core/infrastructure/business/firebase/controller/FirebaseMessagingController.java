package com.backend.core.infrastructure.business.firebase.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.business.firebase.AddNewFcmTokenUseCase;
import com.backend.core.usecase.business.firebase.SendMessageUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class FirebaseMessagingController {
    final UseCaseExecutor useCaseExecutor;
    final SendMessageUseCase sendMessageUseCase;
    final AddNewFcmTokenUseCase addNewFcmTokenUseCase;


    @PostMapping("/unauthen/firebase/sendMessage")
    public CompletableFuture<ResponseEntity<ApiResponse>> sendMessage(@RequestBody String json) throws IOException, FirebaseMessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notification = objectMapper.readValue(json, NotificationDTO.class);

        return useCaseExecutor.execute(
                sendMessageUseCase,
                new SendMessageUseCase.InputValue(notification),
                ResponseMapper::map
        );
    }

    @GetMapping("/authen/firebase/addNewFcmToken={token}")
    public CompletableFuture<ResponseEntity<ApiResponse>> addNewFcmToken(@PathVariable(value = "token") String token, HttpServletRequest httpRequest) {
        return useCaseExecutor.execute(
                addNewFcmTokenUseCase,
                new AddNewFcmTokenUseCase.InputValue(token, httpRequest),
                ResponseMapper::map
        );
    }
}
