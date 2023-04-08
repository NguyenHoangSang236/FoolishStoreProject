package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.InvoiceFilterRequestDTO;
import com.backend.core.entity.dto.ProductFilterRequestDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.InvoiceRepository;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/invoice", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvoiceCustomerController extends CrudController {
    @Autowired
    InvoiceRepository invoiceRepo;


    public InvoiceCustomerController(@Autowired @Qualifier("InvoiceCrudServiceImpl") CrudService invoiceCrudServiceImpl) {
        super(invoiceCrudServiceImpl);
        super.crudService = invoiceCrudServiceImpl;
    }


    @Override
    public ApiResponse addNewItem(String json, HttpSession session) throws IOException {
        return null;
    }


    @Override
    public ApiResponse updateItem(String json, HttpSession session) throws IOException {
        return null;
    }


    @Override
    @GetMapping("/invoice_id={id}")
    public ApiResponse readSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpSession session) throws IOException {
        return crudService.readingById(invoiceId, session);
    }


    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }


    @Override
    public ApiResponse updateSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }


    @Override
    @GetMapping("/showAllCurrentInvoices")
    public ApiResponse getListOfItems(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.CUSTOMER_ALL_CURRENT_INVOICES.name());
    }

    @GetMapping("/showPurchaseHistory")
    public ApiResponse getPurchaseHistory(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.CUSTOMER_PURCHASE_HISTORY.name());
    }


    @Override
    @PostMapping("/filterOrders")
    public ApiResponse getListOfItemsFromFilter(@RequestBody String json, HttpSession session) throws IOException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceFilterRequestDTO invoiceFilterRequestDTO = objectMapper.readValue(json, InvoiceFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(invoiceFilterRequestDTO, session);
    }
}
