package com.backend.core.controller.customer;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.cart.CartItemDTO;
import com.backend.core.entities.requestdto.cart.CartItemFilterRequestDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/cart", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController extends CrudController {
    public CartController(@Autowired @Qualifier("CartCrudServiceImpl") CrudService cartCrudServiceImpl) {
        super(cartCrudServiceImpl);
        super.crudService = cartCrudServiceImpl;
    }



    @PostMapping("/remove")
    public ApiResponse updateCart(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return crudService.removingResponseByRequest(requestDTO, session, httpRequest);
    }


    @PostMapping("/add")
    @Override
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItemDTO = objectMapper.readValue(json, CartItemDTO.class);

        return crudService.singleCreationalResponse(cartItemDTO, session, httpRequest);
    }


    @PostMapping("/update")
    @Override
    public ApiResponse updateItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return crudService.updatingResponseByList(requestDTO, session, httpRequest);
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


    @PostMapping("/showFullCart")
    @Override
    public ApiResponse getListOfItems(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, session, httpRequest);
    }


    @PostMapping("/filterCartItems")
    @Override
    public ApiResponse getListOfItemsFromFilter(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemFilterRequestDTO filterRequest = objectMapper.readValue(json, CartItemFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(filterRequest, session, httpRequest);
    }


    @GetMapping("/totalCartItemQuantity")
    public ApiResponse getTotatalCartItemQuantity(HttpSession session, HttpServletRequest httpRequest) {
        return crudService.readingResponse(session, RenderTypeEnum.TOTAL_CART_ITEM_QUANTITY.name(), httpRequest);
    }


    @GetMapping("/checkout")
    public ApiResponse getCartCheckout(HttpSession session, HttpServletRequest httpRequest) {
        return crudService.readingResponse(session, RenderTypeEnum.CART_CHECKOUT.name(), httpRequest);
    }
}