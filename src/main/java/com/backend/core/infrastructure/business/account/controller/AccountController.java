package com.backend.core.infrastructure.business.account.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.account.gateway.AccountFilterRequestDTO;
import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutorImpl;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.usecases.account.FilterAccountUseCase;
import com.backend.core.usecase.usecases.account.UpdateAccountUseCase;
import com.backend.core.usecase.usecases.account.ViewAccountByIdUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authen/account", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class AccountController {
    private UpdateAccountUseCase updateAccountUseCase;
    private FilterAccountUseCase filterAccountUseCase;
    private ViewAccountByIdUseCase viewAccountByIdUseCase;
    private ResponseMapper responseMapper;
    private UseCaseExecutorImpl useCaseExecutor;


    @PostMapping("/actionOnAccount")
    @Override
    public ResponseEntity<ApiResponse> actionOnAccount(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(json, Account.class);

        return useCaseExecutor.execute(
                updateAccountUseCase,
                new UpdateAccountUseCase.InputValue(account),

        );
    }

    @GetMapping("/account_id={id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> readSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingById(id, httpRequest);
    }

    @PostMapping("/accountList")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountFilterRequestDTO accountFilterRequest = objectMapper.readValue(json, AccountFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(accountFilterRequest, httpRequest);
    }
}
