package com.backend.core.infrastructure.business.refund.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.refund.gateway.RefundConfirmDTO;
import com.backend.core.entity.refund.gateway.RefundFilterRequestDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.business.refund.ConfirmRefundUseCase;
import com.backend.core.usecase.business.refund.FilterRefundUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authen/refund", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class RefundController {
    final UseCaseExecutor useCaseExecutor;
    final FilterRefundUseCase filterRefundUseCase;
    final ConfirmRefundUseCase confirmRefundUseCase;


    @PostMapping("/filterRefundList")
    public CompletableFuture<ResponseEntity<ApiResponse>> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RefundFilterRequestDTO request = objectMapper.readValue(json, RefundFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterRefundUseCase,
                new FilterRefundUseCase.InputValue(request, httpRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/confirmRefund")
    public CompletableFuture<ResponseEntity<ApiResponse>> confirmInvoiceRefund(@RequestParam("evidenceImage") MultipartFile evidenceImage,
                                                                               @RequestParam("invoiceId") int invoiceId,
                                                                               HttpServletRequest httpRequest) {
        RefundConfirmDTO request = RefundConfirmDTO.builder()
                .evidenceImage(evidenceImage)
                .invoiceId(invoiceId)
                .build();

        return useCaseExecutor.execute(
                confirmRefundUseCase,
                new ConfirmRefundUseCase.InputValue(request, httpRequest),
                ResponseMapper::map
        );
    }
}
