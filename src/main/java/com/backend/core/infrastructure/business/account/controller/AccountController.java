package com.backend.core.infrastructure.business.account.controller;

import com.backend.core.entity.account.gateway.AccountFilterRequestDTO;
import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutorImpl;
import com.backend.core.usecase.business.account.FilterAccountUseCase;
import com.backend.core.usecase.business.account.UpdateAccountUseCase;
import com.backend.core.usecase.business.account.ViewAccountByIdUseCase;
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
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authen/account", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountController {
    private UpdateAccountUseCase updateAccountUseCase;
    private FilterAccountUseCase filterAccountUseCase;
    private ViewAccountByIdUseCase viewAccountByIdUseCase;
    private UseCaseExecutorImpl useCaseExecutor;

    @PostMapping("/actionOnAccount")
    public CompletableFuture<ResponseEntity<ApiResponse>> actionOnAccount(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(json, Account.class);

        return useCaseExecutor.execute(
                updateAccountUseCase,
                new UpdateAccountUseCase.InputValue(account),
                ResponseMapper::map
        );
    }

    @GetMapping("/account_id={id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<ResponseEntity<ApiResponse>> getAccountInfoById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return useCaseExecutor.execute(
                viewAccountByIdUseCase,
                new ViewAccountByIdUseCase.InputValue(id),
                ResponseMapper::map
        );
    }

    @PostMapping("/accountList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<ResponseEntity<ApiResponse>> getAccountListByFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountFilterRequestDTO accountFilterRequest = objectMapper.readValue(json, AccountFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterAccountUseCase,
                new FilterAccountUseCase.InputValue(accountFilterRequest),
                ResponseMapper::map
        );
    }
}
