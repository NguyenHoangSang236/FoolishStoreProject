package com.backend.core.infrastructure.business.product.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.usecases.product.AddProductUseCase;
import com.backend.core.usecase.usecases.product.ViewProductByIdUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authen/product", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ProductController {
    final UseCaseExecutor useCaseExecutor;
    final ViewProductByIdUseCase viewProductByIdUseCase;
    final AddProductUseCase addProductUseCase;


    @GetMapping("/product_id={id}")
    public CompletableFuture<ResponseEntity<ApiResponse>> getProductInfoById(@PathVariable(value = "productId") int productId) {
        return useCaseExecutor.execute(
                viewProductByIdUseCase,
                new ViewProductByIdUseCase.InputValue(productId, true),
                ResponseMapper::map
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);
        return null;
//        return crudService.singleCreationalResponse(productDetailsRequest, httpRequest);
    }

    @PostMapping("/edit")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);
        return null;
//        return crudService.updatingResponseByRequest(productDetailsRequest, httpRequest);
    }
}
