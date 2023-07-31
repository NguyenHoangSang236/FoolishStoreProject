package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entities.dto.*;
import com.backend.core.entities.dto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping(value = "/invoice/customer", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvoiceCustomerController extends CrudController {
    public InvoiceCustomerController(@Autowired @Qualifier("InvoiceCrudServiceImpl") CrudService invoiceCrudServiceImpl) {
        super(invoiceCrudServiceImpl);
        super.crudService = invoiceCrudServiceImpl;
    }


    @Override
    @PostMapping("/addNewOrder")
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) {
        Gson gson = new Gson();
        Map<String, String> request = gson.fromJson(json,Map.class);

        return crudService.singleCreationalResponse(request.get("paymentMethod"), session, httpRequest);
    }


    @Override
    public ApiResponse updateItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    @GetMapping("/invoice_id={id}")
    public ApiResponse readSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpSession session, HttpServletRequest httpRequest) {
        return crudService.readingById(invoiceId, session, httpRequest);
    }


    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    @GetMapping("/cancel_order_id={id}")
    public ApiResponse updateSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpSession session, HttpServletRequest httpRequest) {
        return crudService.updatingResponseById(invoiceId, session, httpRequest);
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


    @PostMapping("/onlinePayment")
    public ApiResponse getBankingInfo(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Invoice invoice = objectMapper.readValue(json, Invoice.class);
        return crudService.readingFromSingleRequest(invoice, session, httpRequest);
    }
}
