package com.backend.core.controller.customer;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.cart.CartItemDTO;
import com.backend.core.entities.requestdto.cart.CartItemFilterRequestDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/authen/cart", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController extends CrudController {
    public CartController(@Autowired @Qualifier("CartCrudServiceImpl") CrudService cartCrudServiceImpl) {
        super(cartCrudServiceImpl);
        super.crudService = cartCrudServiceImpl;
    }


    @PostMapping("/remove")
    public ResponseEntity updateCart(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return crudService.removingResponseByRequest(requestDTO, httpRequest);
    }


    @PostMapping("/add")
    @Override
    public ResponseEntity addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItemDTO = objectMapper.readValue(json, CartItemDTO.class);

        return crudService.singleCreationalResponse(cartItemDTO, httpRequest);
    }


    @PostMapping("/update")
    @Override
    public ResponseEntity updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return crudService.updatingResponseByList(requestDTO, httpRequest);
    }

    @Override
    public ResponseEntity readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    public ResponseEntity deleteSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    public ResponseEntity updateSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @PostMapping("/showFullCart")
//    @PreAuthorize("hasRole('CUSTOMER')")
    @Override
    public ResponseEntity getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, httpRequest);
    }


    @PostMapping("/filterCartItems")
    @Override
    public ResponseEntity getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemFilterRequestDTO filterRequest = objectMapper.readValue(json, CartItemFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(filterRequest, httpRequest);
    }


    @GetMapping("/totalCartItemQuantity")
    public ResponseEntity getTotalCartItemQuantity(HttpServletRequest httpRequest) {
        return crudService.readingResponse(RenderTypeEnum.TOTAL_CART_ITEM_QUANTITY.name(), httpRequest);
    }


    @GetMapping("/checkout")
    public ResponseEntity getCartCheckout(HttpServletRequest httpRequest) {
        return crudService.readingResponse(RenderTypeEnum.CART_CHECKOUT.name(), httpRequest);
    }
}