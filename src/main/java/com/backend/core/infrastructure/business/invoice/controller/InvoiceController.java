package com.backend.core.infrastructure.business.invoice.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.invoice.gateway.InvoiceFilterRequestDTO;
import com.backend.core.entity.invoice.gateway.OrderProcessDTO;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.usecase.service.CrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping(value = "/authen/invoice", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class InvoiceController extends CrudController {
    public InvoiceController(@Autowired @Qualifier("InvoiceCrudServiceImpl") CrudService invoiceCrudServiceImpl) {
        super(invoiceCrudServiceImpl);
        super.crudService = invoiceCrudServiceImpl;
    }


    @Override
    @PostMapping("/addNewOrder")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartCheckoutDTO cartCheckout = objectMapper.readValue(json, CartCheckoutDTO.class);

        return crudService.singleCreationalResponse(cartCheckout, httpRequest);
    }


    @PostMapping("/processOrder")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderProcessDTO orderProcess = objectMapper.readValue(json, OrderProcessDTO.class);

        return crudService.updatingResponseByRequest(orderProcess, httpRequest);
    }


    @Override
    @GetMapping("/invoice_id={id}")
    public ResponseEntity<ApiResponse> readSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpServletRequest httpRequest) {
        return crudService.readingById(invoiceId, httpRequest);
    }


    @Override
    public ResponseEntity<ApiResponse> deleteSelectedItemById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    @GetMapping("/cancel_order_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> updateSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpServletRequest httpRequest) {
        return crudService.updatingResponseById(invoiceId, httpRequest);
    }


    @PostMapping("/onlinePayment")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity<ApiResponse> getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Invoice invoice = objectMapper.readValue(json, Invoice.class);
        return crudService.readingFromSingleRequest(invoice, httpRequest);
    }


    @Override
    @PostMapping("/filterOrders")
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceFilterRequestDTO invoiceFilterRequestDTO = objectMapper.readValue(json, InvoiceFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(invoiceFilterRequestDTO, httpRequest);
    }
}
