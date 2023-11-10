package com.backend.core.controller.admin;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.account.AccountFilterRequestDTO;
import com.backend.core.entities.tableentity.Account;
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
@RequestMapping(value = "/authen/account", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class AccountController extends CrudController {
    public AccountController(@Autowired @Qualifier("AccountCrudServiceImpl") CrudService accountCrudServiceImpl) {
        super(accountCrudServiceImpl);
        super.crudService = accountCrudServiceImpl;
    }

    @Override
    public ResponseEntity<ApiResponse> addNewItem(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @PostMapping("/actionOnAccount")
    @Override
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account acc = objectMapper.readValue(json, Account.class);
        return crudService.updatingResponseByRequest(acc, httpRequest);
    }

    @GetMapping("/account_id={id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> readSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingById(id, httpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @PostMapping("/allAccounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, httpRequest);
    }

    @PostMapping("/filterAccounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountFilterRequestDTO accountFilterRequest = objectMapper.readValue(json, AccountFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(accountFilterRequest, httpRequest);
    }
}
