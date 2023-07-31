package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entities.dto.ApiResponse;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/category", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController extends CrudController {

    public CategoryController(@Autowired @Qualifier("CategoryCrudServiceImpl") CrudService cartCrudServiceImpl) {
        super(cartCrudServiceImpl);
        super.crudService = cartCrudServiceImpl;
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

    @Override
    @GetMapping("/allCategories")
    public ApiResponse getListOfItems(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.ALL_CATEGORIES.name(),httpRequest);
    }

    @Override
    public ApiResponse getListOfItemsFromFilter(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
