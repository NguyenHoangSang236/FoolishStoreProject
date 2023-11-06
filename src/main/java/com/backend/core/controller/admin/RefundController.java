package com.backend.core.controller.admin;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.refund.RefundFilterRequestDTO;
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
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authen/refund", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RefundController extends CrudController {
    public RefundController(@Autowired @Qualifier("RefundCrudServiceImpl") CrudService refundCrudServiceImpl) {
        super(refundCrudServiceImpl);
        super.crudService = refundCrudServiceImpl;
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
    @PostMapping("/filterRefundList")
    public ResponseEntity getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RefundFilterRequestDTO request = objectMapper.readValue(json, RefundFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(request, httpRequest);
    }
}
