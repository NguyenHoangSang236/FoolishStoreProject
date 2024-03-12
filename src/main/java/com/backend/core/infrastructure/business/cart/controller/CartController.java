package com.backend.core.infrastructure.business.cart.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.entity.cart.gateway.AddressNameDTO;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.cart.gateway.CartItemDTO;
import com.backend.core.entity.cart.gateway.CartItemFilterRequestDTO;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.RenderTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping(value = "/authen/cart", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasAuthority('CUSTOMER')")
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class CartController extends CrudController {
    public CartController(@Autowired @Qualifier("CartCrudServiceImpl") CrudService cartCrudServiceImpl) {
        super(cartCrudServiceImpl);
        super.crudService = cartCrudServiceImpl;
    }


    @PostMapping("/remove")
    public ResponseEntity<ApiResponse> updateCart(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return crudService.removingResponseByRequest(requestDTO, httpRequest);
    }


    @PostMapping("/add")
    @Override
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItemDTO = objectMapper.readValue(json, CartItemDTO.class);

        return crudService.singleCreationalResponse(cartItemDTO, httpRequest);
    }


    @PostMapping("/update")
    @Override
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return crudService.updatingResponseByList(requestDTO, httpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse> readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> deleteSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @PostMapping("/showFullCart")
    @Override
    public ResponseEntity<ApiResponse> getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, httpRequest);
    }


    @PostMapping("/filterCartItems")
    @Override
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemFilterRequestDTO filterRequest = objectMapper.readValue(json, CartItemFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(filterRequest, httpRequest);
    }


    @GetMapping("/totalCartItemQuantity")
    public ResponseEntity<ApiResponse> getTotalCartItemQuantity(HttpServletRequest httpRequest) {
        return crudService.readingResponse(RenderTypeEnum.TOTAL_CART_ITEM_QUANTITY.name(), httpRequest);
    }


    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> getCartCheckout(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartCheckoutDTO checkout = objectMapper.readValue(json, CartCheckoutDTO.class);
        return crudService.readingFromSingleRequest(checkout, httpRequest);
    }

    @PostMapping("/getGnhAvailableServiceList")
    public ResponseEntity<ApiResponse> getGhnServiceList(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressCodeDTO addressCode = objectMapper.readValue(json, AddressCodeDTO.class);
        return crudService.readingFromSingleRequest(addressCode, httpRequest);
    }


    @PostMapping("/getGhnAddressCode")
    public ResponseEntity<ApiResponse> getGhnAddressCode(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressNameDTO addressName = objectMapper.readValue(json, AddressNameDTO.class);
        return crudService.readingFromSingleRequest(addressName, httpRequest);
    }
}