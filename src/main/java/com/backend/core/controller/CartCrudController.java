package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.repository.CartRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.repository.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartCrudController extends CrudController {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;


    public CartCrudController(CrudService crudService) {
        super(crudService);
    }


    @GetMapping("/showFullCart")
    public ApiResponse getCartListByCustomerId(HttpSession session) {
        return crudService.readingListGettingReponse(session);
    }


    @PostMapping("/remove")
    public ApiResponse updateCart(@RequestBody int[] selectedCartIdArr, HttpSession session) {
        return crudService.removingResponse(selectedCartIdArr, session);
    }


    @PostMapping("/add")
    @Override
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItemDTO = objectMapper.readValue(json, CartItemDTO.class);;

        return crudService.creationalResponse(cartItemDTO, session);
    }


    @PostMapping("/update")
    @Override
    public ApiResponse updateItem(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> objectList = objectMapper.readValue(json, new TypeReference<List<Object>>(){});

        return crudService.updatingResponse(objectList, session);
    }
}