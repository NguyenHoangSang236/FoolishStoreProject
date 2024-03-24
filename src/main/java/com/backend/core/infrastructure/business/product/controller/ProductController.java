package com.backend.core.infrastructure.business.product.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.usecases.product.AddProductUseCase;
import com.backend.core.usecase.usecases.product.EditGeneralProductInfoUseCase;
import com.backend.core.usecase.usecases.product.EditProductPropertiesUseCase;
import com.backend.core.usecase.usecases.product.ViewProductByIdUseCase;
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
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authen/product", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ProductController {
    final UseCaseExecutor useCaseExecutor;
    final ViewProductByIdUseCase viewProductByIdUseCase;
    final AddProductUseCase addProductUseCase;
    final EditGeneralProductInfoUseCase editGeneralProductInfoUseCase;
    final EditProductPropertiesUseCase editProductPropertiesUseCase;


    @GetMapping("/product_id={id}")
    public CompletableFuture<ResponseEntity<ApiResponse>> getProductInfoById(@PathVariable(value = "id") int productId) {
        return useCaseExecutor.execute(
                viewProductByIdUseCase,
                new ViewProductByIdUseCase.InputValue(productId, true),
                ResponseMapper::map
        );
    }

    @PostMapping("/add")
    public CompletableFuture<ResponseEntity<ApiResponse>> addNewProduct(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);

        return useCaseExecutor.execute(
                addProductUseCase,
                new AddProductUseCase.InputValue(productDetailsRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/editGeneralInfo")
    public CompletableFuture<ResponseEntity<ApiResponse>> editGeneralProductInfo(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);

        return useCaseExecutor.execute(
                editGeneralProductInfoUseCase,
                new EditGeneralProductInfoUseCase.InputValue(productDetailsRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/editProductProperties")
    public CompletableFuture<ResponseEntity<ApiResponse>> editProductPropertiesInfo(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDetailsRequestDTO productDetailsRequest = objectMapper.readValue(json, ProductDetailsRequestDTO.class);

        return useCaseExecutor.execute(
                editProductPropertiesUseCase,
                new EditProductPropertiesUseCase.InputValue(productDetailsRequest),
                ResponseMapper::map
        );
    }
}
