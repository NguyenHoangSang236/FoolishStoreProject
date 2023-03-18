package com.backend.core.abstractclasses;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nullable;
import java.io.IOException;

public abstract class CrudController {
    protected CrudService crudService;

    public CrudController(CrudService crudService) {
        this.crudService = crudService;
    }

    public abstract ApiResponse addNewItem(@RequestBody String json, HttpSession session) throws IOException;

    public abstract ApiResponse updateItem(@RequestBody String json, HttpSession session) throws IOException;
//    public abstract ApiResponse deleteSelectedItemById(int id);
//
//    public abstract ApiResponse updateSelectedItemById(int id);
//
    public abstract ApiResponse getListOfItems(@RequestBody String json, HttpSession session) throws IOException;

//    public abstract ApiResponse getItem();
}
