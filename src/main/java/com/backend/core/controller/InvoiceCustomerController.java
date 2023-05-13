package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entity.dto.*;
import com.backend.core.repository.InvoiceRepository;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/invoice/customer", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvoiceCustomerController extends CrudController {
    @Autowired
    InvoiceRepository invoiceRepo;


    public InvoiceCustomerController(@Autowired @Qualifier("InvoiceCrudServiceImpl") CrudService invoiceCrudServiceImpl) {
        super(invoiceCrudServiceImpl);
        super.crudService = invoiceCrudServiceImpl;
    }


    @Override
    @PostMapping("/addNewOrder")
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        List<Object> objList = objMapper.readValue(json, new TypeReference<List<Object>>() {});

        return crudService.listCreationalResponse(objList, session, httpRequest);
    }


    @Override
    public ApiResponse updateItem(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    @GetMapping("/invoice_id={id}")
    public ApiResponse readSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingById(invoiceId, session, httpRequest);
    }


    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    public ApiResponse updateSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    @PostMapping("/showAllCurrentInvoices")
    public ApiResponse getListOfItems(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, session, httpRequest);
    }


    @GetMapping("/showPurchaseHistory")
    public ApiResponse getPurchaseHistory(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, session, httpRequest);
    }


    @Override
    @PostMapping("/filterOrders")
    public ApiResponse getListOfItemsFromFilter(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceFilterRequestDTO invoiceFilterRequestDTO = objectMapper.readValue(json, InvoiceFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(invoiceFilterRequestDTO, session, httpRequest);
    }
}
