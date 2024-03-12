package com.backend.core.entity;

import com.backend.core.usecase.service.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public abstract class CrudController {
    protected CrudService crudService;

    public CrudController(CrudService crudService) {
        this.crudService = crudService;
    }

    public abstract ResponseEntity addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException;

    public abstract ResponseEntity updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException;

    public abstract ResponseEntity readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException;

    public abstract ResponseEntity deleteSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException;

    public abstract ResponseEntity updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException;

    public abstract ResponseEntity getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException;

    public abstract ResponseEntity getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException;
}
