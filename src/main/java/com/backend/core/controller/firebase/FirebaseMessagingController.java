package com.backend.core.controller.firebase;

import com.backend.core.entities.requestdto.notification.NotificationDTO;
import com.backend.core.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class FirebaseMessagingController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("/unauthen/firebase/sendMessage")
    public ResponseEntity sendMessage(@RequestBody String json, HttpServletRequest httpRequest) throws IOException, FirebaseMessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notification = objectMapper.readValue(json, NotificationDTO.class);

        return notificationService.sendMessage(notification, httpRequest);
    }

    @GetMapping("/authen/firebase/addNewFcmToken={token}")
    public ResponseEntity addNewItem(@PathVariable(value = "token") String token, HttpServletRequest httpRequest) throws IOException {
        return notificationService.addNewDeviceFcmToken(token, httpRequest);
    }

    @PostMapping("/unauthen/firebase/subscribeToTopic")
    public ResponseEntity subscribeToTopic(@RequestBody String json, HttpServletRequest httpRequest) throws IOException, FirebaseMessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notification = objectMapper.readValue(json, NotificationDTO.class);

        return notificationService.subscribeToTopic(notification, httpRequest);
    }

    @PostMapping("/unauthen/firebase/unsubscribeFromTopic")
    public ResponseEntity unsubscribeFromTopic(@RequestBody String json, HttpServletRequest httpRequest) throws IOException, FirebaseMessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notification = objectMapper.readValue(json, NotificationDTO.class);

        return notificationService.unsubscribeFromTopic(notification, httpRequest);
    }
}
