package com.backend.core.controller.common;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.notification.NotificationFilterRequestDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/authen/notification", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController extends CrudController {
    public NotificationController(@Autowired @Qualifier("NotificationCrudServiceImpl") CrudService notificationCrudServiceImpl) {
        super(notificationCrudServiceImpl);
        super.crudService = notificationCrudServiceImpl;
    }

    @Override
    public ResponseEntity addNewItem(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
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
    @PostMapping("/filterNotifications")
    public ResponseEntity getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationFilterRequestDTO filterRequest = objectMapper.readValue(json, NotificationFilterRequestDTO.class);

        return crudService.readingFromSingleRequest(filterRequest, httpRequest);
    }
}
