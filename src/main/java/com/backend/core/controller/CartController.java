package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.CartRenderInfoRepository;
import com.backend.core.repository.CartRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.repository.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.backend.core.serviceimpl.CartCrudServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/cart", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController extends CrudController {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ProductManagementRepository productRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;



    public CartController(@Autowired @Qualifier("CartCrudServiceImpl") CrudService cartCrudServiceImpl) {
        super(cartCrudServiceImpl);
        super.crudService = cartCrudServiceImpl;
    }



    @PostMapping("/remove")
    public ApiResponse updateCart(@RequestBody int[] selectedCartIdArr, HttpSession session) {
        return crudService.removingResponse(selectedCartIdArr, session);
    }


    @PostMapping("/add")
    @Override
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItemDTO = objectMapper.readValue(json, CartItemDTO.class);

        return crudService.singleCreationalResponse(cartItemDTO, session);
    }


    @PostMapping("/update")
    @Override
    public ApiResponse updateItem(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> objectList = objectMapper.readValue(json, new TypeReference<List<Object>>(){});

        return crudService.updatingResponse(objectList, session);
    }

    @Override
    public ApiResponse readSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }


    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }


    @Override
    public ApiResponse updateSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }


    @GetMapping("/showFullCart")
    @Override
    public ApiResponse getListOfItems(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.ALL_CART_ITEMS.name());
    }


    @Override
    public ApiResponse getListOfItemsFromFilter(String json, HttpSession session) throws IOException {
        return null;
    }
}