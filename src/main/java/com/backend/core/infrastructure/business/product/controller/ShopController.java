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
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRenderInfoDTO request = objectMapper.readValue(json, ProductRenderInfoDTO.class);

        return null;
//        return crudService.updatingResponseByRequest(request, httpRequest);
    }

    @GetMapping("/unauthen/shop/top8BestSellers")
    public CompletableFuture<ResponseEntity<ApiResponse>> getTop8BestSellers() throws IOException {
        return useCaseExecutor.execute(
                bestSellerProductsUseCase,
                new BestSellerProductsUseCase.InputValue(),
                ResponseMapper::map
        );
    }


    @GetMapping("/unauthen/shop/newArrivalProducts")
    public CompletableFuture<ResponseEntity<ApiResponse>> getNewArrivalProducts(String json, HttpServletRequest httpRequest) throws IOException {
        return useCaseExecutor.execute(
                newArrivalProductsUseCase,
                new NewArrivalProductsUseCase.InputValue(),
                ResponseMapper::map
        );
    }


    @GetMapping("/unauthen/shop/hotDiscountProducts")
    public CompletableFuture<ResponseEntity<ApiResponse>> getHotDiscountProducts(String json, HttpServletRequest httpRequest) throws IOException {
        return useCaseExecutor.execute(
                hotDiscountProductsUseCase,
                new HotDiscountProductsUseCase.InputValue(),
                ResponseMapper::map
        );
    }

    @GetMapping("/unauthen/shop/totalProductsQuantity")
    public ResponseEntity<ApiResponse> getTotalProductsQuantity(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
//        return crudService.readingResponse(RenderTypeEnum.TOTAL_PRODUCTS_QUANTITY.name(), httpRequest);
    }

    @GetMapping("/unauthen/shop/productSizeList")
    public ResponseEntity<ApiResponse> getSizeListOfProduct(@RequestParam(value = "productId") int productId, @RequestParam(value = "color") String color, HttpServletRequest httpRequest) throws IOException {
        Map<String, Object> request = new HashMap<>();

        request.put("productId", productId);
        request.put("color", color);

        return null;
//        return crudService.readingFromSingleRequest(request, httpRequest);
    }

    @GetMapping("/unauthen/shop/product_id={productId}")
    public ResponseEntity<ApiResponse> getProductInfo(@PathVariable(value = "productId") int productId, @RequestParam(value = "showFull") String showFull, HttpServletRequest httpRequest) throws IOException {
        Map<String, Object> request = new HashMap<>();

        request.put("productId", productId);
        request.put("showFull", showFull);

        return null;
//        return crudService.readingFromSingleRequest(request, httpRequest);
    }
}
