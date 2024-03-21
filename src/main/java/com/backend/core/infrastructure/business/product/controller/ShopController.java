package com.backend.core.infrastructure.business.product.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.entity.product.gateway.ProductFilterRequestDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.service.CalculationService;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.RenderTypeEnum;
import com.backend.core.usecase.usecases.product.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ShopController {
    final UseCaseExecutor useCaseExecutor;
    final FilterProductUseCase filterProductUseCase;
    final GetAllProductsUseCase getAllProductsUseCase;
    final HotDiscountProductsUseCase hotDiscountProductsUseCase;
    final NewArrivalProductsUseCase newArrivalProductsUseCase;
    final BestSellerProductsUseCase bestSellerProductsUseCase;
    final TotalProductQuantityUseCase totalProductQuantityUseCase;
    final ProductSizeListUseCase productSizeListUseCase;
    final ViewProductByIdUseCase viewProductByIdUseCase;
    final RateProductUseCase rateProductUseCase;


    @PostMapping("/unauthen/shop/productList")
    public CompletableFuture<ResponseEntity<ApiResponse>> getAllProducts(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);

        return useCaseExecutor.execute(
                getAllProductsUseCase,
                new GetAllProductsUseCase.InputValue(pagination),
                ResponseMapper::map
        );
    }

    @PostMapping("/unauthen/shop/filterProduct")
    public CompletableFuture<ResponseEntity<ApiResponse>> filterProducts(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductFilterRequestDTO productFilterRequest = objectMapper.readValue(json, ProductFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterProductUseCase,
                new FilterProductUseCase.InputValue(productFilterRequest, httpRequest),
                ResponseMapper::map
        );
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/authen/shop/rateProduct")
    public CompletableFuture<ResponseEntity<ApiResponse>> rateProduct(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRenderInfoDTO request = objectMapper.readValue(json, ProductRenderInfoDTO.class);

        return useCaseExecutor.execute(
                rateProductUseCase,
                new RateProductUseCase.InputValue(request),
                ResponseMapper::map
        );
    }

    @GetMapping("/unauthen/shop/top8BestSellers")
    public CompletableFuture<ResponseEntity<ApiResponse>> getTop8BestSellers() {
        return useCaseExecutor.execute(
                bestSellerProductsUseCase,
                new BestSellerProductsUseCase.InputValue(),
                ResponseMapper::map
        );
    }


    @GetMapping("/unauthen/shop/newArrivalProducts")
    public CompletableFuture<ResponseEntity<ApiResponse>> getNewArrivalProducts() {
        return useCaseExecutor.execute(
                newArrivalProductsUseCase,
                new NewArrivalProductsUseCase.InputValue(),
                ResponseMapper::map
        );
    }


    @GetMapping("/unauthen/shop/hotDiscountProducts")
    public CompletableFuture<ResponseEntity<ApiResponse>> getHotDiscountProducts() {
        return useCaseExecutor.execute(
                hotDiscountProductsUseCase,
                new HotDiscountProductsUseCase.InputValue(),
                ResponseMapper::map
        );
    }

    @GetMapping("/unauthen/shop/totalProductsQuantity")
    public CompletableFuture<ResponseEntity<ApiResponse>> getTotalProductsQuantity() {
        return useCaseExecutor.execute(
                totalProductQuantityUseCase,
                new TotalProductQuantityUseCase.InputValue(),
                ResponseMapper::map
        );
    }

    @GetMapping("/unauthen/shop/productSizeList")
    public CompletableFuture<ResponseEntity<ApiResponse>> getSizeListOfProduct(@RequestParam(value = "productId") int productId, @RequestParam(value = "color") String color) {
        return useCaseExecutor.execute(
                productSizeListUseCase,
                new ProductSizeListUseCase.InputValue(color, productId),
                ResponseMapper::map
        );
    }

    @GetMapping("/unauthen/shop/product_id={productId}")
    public CompletableFuture<ResponseEntity<ApiResponse>> getProductInfoById(@PathVariable(value = "productId") int productId) {
        return useCaseExecutor.execute(
                viewProductByIdUseCase,
                new ViewProductByIdUseCase.InputValue(productId, false),
                ResponseMapper::map
        );
    }
}
