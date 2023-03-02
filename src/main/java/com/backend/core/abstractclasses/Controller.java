package com.backend.core.abstractclasses;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public abstract class Controller {
    protected CrudService crudService;

    public Controller(CrudService crudService) {
        this.crudService = crudService;
    }

    public abstract ApiResponse addNewItem(@RequestBody String json, HttpSession session) throws IOException;

//    public abstract ApiResponse deleteSelectedItemById(int id);
//
//    public abstract ApiResponse updateSelectedItemById(int id);
//
//    public abstract ApiResponse getListOfItems();
//
//    public abstract ApiResponse getItem();
}
