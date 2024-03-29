package com.backend.core.infrastructure.business.cart.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.cart.gateway.AddressNameDTO;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.cart.gateway.CartItemDTO;
import com.backend.core.entity.cart.gateway.CartItemFilterRequestDTO;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.business.cart.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/authen/cart", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasAuthority('CUSTOMER')")
@AllArgsConstructor
public class CartController {
    final UseCaseExecutor useCaseExecutor;
    final FilterCartUseCase filterCartUseCase;
    final TotalCartItemQuantityUseCase totalCartItemQuantityUseCase;
    final UpdateCartUseCase updateCartUseCase;
    final AddCartItemUseCase addCartItemUseCase;
    final RemoveCartItemUseCase removeCartItemUseCase;
    final GetGhnAddressCodeUseCase getGhnAddressCodeUseCase;
    final GetGhnAvailableServiceListUseCase getGhnAvailableServiceListUseCase;
    final CheckoutUseCase checkoutUseCase;


    @PostMapping("/remove")
    public CompletableFuture<ResponseEntity<ApiResponse>> updateCart(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return useCaseExecutor.execute(
                removeCartItemUseCase,
                new RemoveCartItemUseCase.InputValue(requestDTO, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/add")
    public CompletableFuture<ResponseEntity<ApiResponse>> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItem = objectMapper.readValue(json, CartItemDTO.class);

        return useCaseExecutor.execute(
                addCartItemUseCase,
                new AddCartItemUseCase.InputValue(cartItem, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/update")
    public CompletableFuture<ResponseEntity<ApiResponse>> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListRequestDTO requestDTO = objectMapper.readValue(json, ListRequestDTO.class);

        return useCaseExecutor.execute(
                updateCartUseCase,
                new UpdateCartUseCase.InputValue(requestDTO, httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/filterCartItems")
    public CompletableFuture<ResponseEntity<ApiResponse>> filterCartItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemFilterRequestDTO filterRequest = objectMapper.readValue(json, CartItemFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterCartUseCase,
                new FilterCartUseCase.InputValue(filterRequest, httpRequest),
                ResponseMapper::map
        );
    }


    @GetMapping("/totalCartItemQuantity")
    public CompletableFuture<ResponseEntity<ApiResponse>> getTotalCartItemQuantity(HttpServletRequest httpRequest) {
        return useCaseExecutor.execute(
                totalCartItemQuantityUseCase,
                new TotalCartItemQuantityUseCase.InputValue(httpRequest),
                ResponseMapper::map
        );
    }


    @PostMapping("/checkout")
    public CompletableFuture<ResponseEntity<ApiResponse>> getCartCheckout(@RequestBody String json, HttpServletRequest httpRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartCheckoutDTO checkout = objectMapper.readValue(json, CartCheckoutDTO.class);

        return useCaseExecutor.execute(
                checkoutUseCase,
                new CheckoutUseCase.InputValue(checkout, httpRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/getGnhAvailableServiceList")
    public CompletableFuture<ResponseEntity<ApiResponse>> getGhnServiceList(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressCodeDTO addressCode = objectMapper.readValue(json, AddressCodeDTO.class);

        return useCaseExecutor.execute(
                getGhnAvailableServiceListUseCase,
                new GetGhnAvailableServiceListUseCase.InputValue(addressCode),
                ResponseMapper::map
        );
    }


    @PostMapping("/getGhnAddressCode")
    public CompletableFuture<ResponseEntity<ApiResponse>> getGhnAddressCode(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressNameDTO addressName = objectMapper.readValue(json, AddressNameDTO.class);

        return useCaseExecutor.execute(
                getGhnAddressCodeUseCase,
                new GetGhnAddressCodeUseCase.InputValue(addressName),
                ResponseMapper::map
        );
    }
}