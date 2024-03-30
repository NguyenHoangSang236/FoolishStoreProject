package com.backend.core.infrastructure.business.invoice.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.invoice.gateway.InvoiceFilterRequestDTO;
import com.backend.core.entity.invoice.gateway.OrderProcessDTO;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.business.invoice.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/authen/invoice", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class InvoiceController {
    final UseCaseExecutor useCaseExecutor;
    final AddNewOrderUseCase addNewOrderUseCase;
    final ProcessOrderUseCase processOrderUseCase;
    final CancelOrderByIdUseCase cancelOrderByIdUseCase;
    final ViewInvoiceByIdUseCase viewInvoiceByIdUseCase;
    final ViewOnlinePaymentInfoUseCase viewOnlinePaymentInfoUseCase;
    final FilterInvoiceUseCase filterInvoiceUseCase;


    @PostMapping("/addNewOrder")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> addNewOrder(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartCheckoutDTO cartCheckout = objectMapper.readValue(json, CartCheckoutDTO.class);

        return useCaseExecutor.execute(
                addNewOrderUseCase,
                new AddNewOrderUseCase.InputValue(cartCheckout, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/processOrder")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<ResponseEntity<ApiResponse>> processOrder(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderProcessDTO orderProcess = objectMapper.readValue(json, OrderProcessDTO.class);

        return useCaseExecutor.execute(
                processOrderUseCase,
                new ProcessOrderUseCase.InputValue(orderProcess, httpRequest),
                ResponseMapper::map
        );
    }


    @GetMapping("/invoice_id={id}")
    public CompletableFuture<ResponseEntity<ApiResponse>> viewInvoiceInfoById(@PathVariable(value = "id") int invoiceId, HttpServletRequest httpRequest) {
        return useCaseExecutor.execute(
                viewInvoiceByIdUseCase,
                new ViewInvoiceByIdUseCase.InputValue(invoiceId, httpRequest),
                ResponseMapper::map
        );
    }


    @GetMapping("/cancel_order_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> updateSelectedItemById(@PathVariable(value = "id") int invoiceId, HttpServletRequest httpRequest) {
        return useCaseExecutor.execute(
                cancelOrderByIdUseCase,
                new CancelOrderByIdUseCase.InputValue(invoiceId, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/onlinePayment")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Invoice invoice = objectMapper.readValue(json, Invoice.class);

        return useCaseExecutor.execute(
                viewOnlinePaymentInfoUseCase,
                new ViewOnlinePaymentInfoUseCase.InputValue(invoice, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/filterOrders")
    public CompletableFuture<ResponseEntity<ApiResponse>> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceFilterRequestDTO invoiceFilterRequestDTO = objectMapper.readValue(json, InvoiceFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterInvoiceUseCase,
                new FilterInvoiceUseCase.InputValue(invoiceFilterRequestDTO, httpRequest),
                ResponseMapper::map
        );
    }
}
