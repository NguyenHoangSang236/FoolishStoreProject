package com.backend.core.controller.common;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.requestdto.invoice.OrderProcessDTO;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/authen/invoice", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvoiceController extends CrudController {
    public InvoiceController(@Autowired @Qualifier("InvoiceCrudServiceImpl") CrudService invoiceCrudServiceImpl) {
        super(invoiceCrudServiceImpl);
        super.crudService = invoiceCrudServiceImpl;
    }


    @Override
    @PostMapping("/addNewOrder")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity addNewItem(@RequestBody String json, HttpServletRequest httpRequest) {
        Gson gson = new Gson();
        Map<String, String> request = gson.fromJson(json, Map.class);

        return crudService.singleCreationalResponse(request.get("paymentMethod"), httpRequest);
    }


    @PostMapping("/processOrder")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderProcessDTO orderProcess = objectMapper.readValue(json, OrderProcessDTO.class);

        return crudService.updatingResponseByRequest(orderProcess, httpRequest);
    }


    @Override
    @GetMapping("/invoice_id={id}")
    public ResponseEntity readSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpServletRequest httpRequest) {
        return crudService.readingById(invoiceId, httpRequest);
    }


    @Override
    public ResponseEntity deleteSelectedItemById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    @GetMapping("/cancel_order_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity updateSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpServletRequest httpRequest) {
        return crudService.updatingResponseById(invoiceId, httpRequest);
    }


    @PostMapping("/onlinePayment")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Invoice invoice = objectMapper.readValue(json, Invoice.class);
        return crudService.readingFromSingleRequest(invoice, httpRequest);
    }


    @Override
    @PostMapping("/filterOrders")
    public ResponseEntity getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceFilterRequestDTO invoiceFilterRequestDTO = objectMapper.readValue(json, InvoiceFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(invoiceFilterRequestDTO, httpRequest);
    }
}
