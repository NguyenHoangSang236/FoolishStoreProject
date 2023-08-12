package com.backend.core.controller.admin;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/authen/account", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController extends CrudController {
    public AccountController(@Autowired @Qualifier("AccountCrudServiceImpl") CrudService accountCrudServiceImpl) {
        super(accountCrudServiceImpl);
        super.crudService = accountCrudServiceImpl;
    }

    @Override
    public ResponseEntity addNewItem(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @PostMapping("/actionOnAccount")
    @Override
    public ResponseEntity updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account acc = objectMapper.readValue(json, Account.class);
        return crudService.updatingResponseByRequest(acc, httpRequest);
    }

    @Override
    public ResponseEntity readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity deleteSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @PostMapping("/allAccounts")
    @Override
    public ResponseEntity getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, httpRequest);
    }

    @Override
    public ResponseEntity getListOfItemsFromFilter(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
