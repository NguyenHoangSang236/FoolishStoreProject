package com.backend.core.controller.admin;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.product.ProductDetailsRequestDTO;
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
@RequestMapping(value = "/authen/product", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class ProductController extends CrudController {
    public ProductController(@Autowired @Qualifier("ProductCrudServiceImpl") CrudService productCrudServiceImpl) {
        super(productCrudServiceImpl);
        super.crudService = productCrudServiceImpl;
    }


    @PostMapping("/add")
    @Override
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);
        return crudService.singleCreationalResponse(productDetailsRequest, httpRequest);
    }

    @PostMapping("/edit")
    @Override
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);
        return crudService.updatingResponseByRequest(productDetailsRequest, httpRequest);
    }

    @GetMapping("/product_id={id}")
    @Override
    public ResponseEntity<ApiResponse> readSelectedItemById(@PathVariable(value = "id") int id, HttpServletRequest httpRequest) throws IOException {
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

    @Override
    public ResponseEntity<ApiResponse> getListOfItems(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
