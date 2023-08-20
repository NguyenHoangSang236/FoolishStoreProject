package com.backend.core.controller.shipper;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.delivery.DeliveryActionOnOrderDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryFilterRequestDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/authen/delivery", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasAuthority('SHIPPER')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeliveryController extends CrudController {
    public DeliveryController(@Autowired @Qualifier("DeliveryCrudServiceImpl") CrudService deliveryCrudService) {
        super(deliveryCrudService);
        super.crudService = deliveryCrudService;
    }


    @Override
    public ResponseEntity addNewItem(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @PostMapping("/actionOnOrder")
    @Override
    public ResponseEntity updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DeliveryActionOnOrderDTO action = objectMapper.readValue(json, DeliveryActionOnOrderDTO.class);
        return crudService.updatingResponseByRequest(action, httpRequest);
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

    @PostMapping("/filterMyShippingOrders")
    @Override
    public ResponseEntity getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DeliveryFilterRequestDTO deliveryFilterRequest = objectMapper.readValue(json, DeliveryFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(deliveryFilterRequest, httpRequest);
    }
}
