package com.backend.core.abstractclasses;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public abstract class CrudController {
    protected CrudService crudService;

    public CrudController(CrudService crudService) {
        this.crudService = crudService;
    }

    public abstract ApiResponse addNewItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException;

    public abstract ApiResponse updateItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException;

    public abstract ApiResponse readSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException;

    public abstract ApiResponse deleteSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException;

    public abstract ApiResponse updateSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException;

    public abstract ApiResponse getListOfItems(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException;

    public abstract ApiResponse getListOfItemsFromFilter(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException;
}
