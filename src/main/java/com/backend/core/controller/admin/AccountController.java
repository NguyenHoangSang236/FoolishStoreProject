package com.backend.core.controller.admin;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/account", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController extends CrudController {
    public AccountController(@Autowired @Qualifier("AccountCrudServiceImpl") CrudService accountCrudServiceImpl) {
        super(accountCrudServiceImpl);
        super.crudService = accountCrudServiceImpl;
    }

    @Override
    public ApiResponse addNewItem(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ApiResponse updateItem(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ApiResponse readSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ApiResponse updateSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @PostMapping("/allAccounts")
    @Override
    public ApiResponse getListOfItems(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, session, httpRequest);
    }

    @Override
    public ApiResponse getListOfItemsFromFilter(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
