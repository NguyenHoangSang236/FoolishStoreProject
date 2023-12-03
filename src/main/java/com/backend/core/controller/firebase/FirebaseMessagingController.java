package com.backend.core.controller.firebase;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.service.CrudService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class FirebaseMessagingController extends CrudController {


    public FirebaseMessagingController(@Autowired @Qualifier("FirebaseServiceImpl") CrudService firebaseServiceImpl) {
        super(firebaseServiceImpl);
        super.crudService = firebaseServiceImpl;
    }

    @PostMapping("/unauthen/firebase/sendMessage")
    public ResponseEntity sendMessage(@RequestBody String json, HttpServletRequest httpRequest) throws IOException, FirebaseMessagingException {
        Message msg = Message.builder()
                .setTopic("all")
                .putData("body", json)
                .build();

        final FirebaseMessaging fcm = FirebaseMessaging.getInstance();

        String id = fcm.send(msg);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }

    @Override
    @GetMapping("/authen/firebase/addNewFcmToken={token}")
    public ResponseEntity addNewItem(@PathVariable(value = "token") String token, HttpServletRequest httpRequest) throws IOException {
        return crudService.singleCreationalResponse(token, httpRequest);
    }

    @Override
    public ResponseEntity updateItem(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity deleteSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity getListOfItems(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity getListOfItemsFromFilter(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
