package com.backend.core.controller.common;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.tableentity.Catalog;
import com.backend.core.enums.RenderTypeEnum;
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
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class CategoryController extends CrudController {

    public CategoryController(@Autowired @Qualifier("CategoryCrudServiceImpl") CrudService categoryCrudServiceImpl) {
        super(categoryCrudServiceImpl);
        super.crudService = categoryCrudServiceImpl;
    }

    @PostMapping("/authen/category/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Catalog catalog = objectMapper.readValue(json, Catalog.class);
        return crudService.singleCreationalResponse(catalog, httpRequest);
    }

    @PostMapping("/authen/category/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Catalog catalog = objectMapper.readValue(json, Catalog.class);
        return crudService.updatingResponseByRequest(catalog, httpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse> readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @GetMapping("authen/category/delete_category_id={id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse> deleteSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return crudService.removingResponseById(id, httpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse> updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    @GetMapping("/unauthen/category/allCategories")
    public ResponseEntity<ApiResponse> getListOfItems(String json, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(RenderTypeEnum.ALL_CATEGORIES.name(), httpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
