package com.backend.core.controller.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/unauthen/testFcm", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class FirebaseMessagingController {
    private final FirebaseMessaging fcm;

    public FirebaseMessagingController(FirebaseMessaging fcm) {
        this.fcm = fcm;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException, FirebaseMessagingException {
        Message msg = Message.builder()
                .setTopic("all")
                .putData("body", json)
                .build();

        String id = fcm.send(msg);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }
}
