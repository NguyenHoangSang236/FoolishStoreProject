package com.backend.core.controller.admin;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.delivery.DeliveryRequestDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/authen/delivery", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasAuthority('ADMIN')")
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class DeliveryController extends CrudController {
    public DeliveryController(@Autowired @Qualifier("DeliveryCrudServiceImpl") CrudService deliveryCrudService) {
        super(deliveryCrudService);
        super.crudService = deliveryCrudService;
    }


    @Override
    @PostMapping("/addNewShippingOrder")
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DeliveryRequestDTO request = objectMapper.readValue(json, DeliveryRequestDTO.class);

        return crudService.singleCreationalResponse(request, httpRequest);
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
