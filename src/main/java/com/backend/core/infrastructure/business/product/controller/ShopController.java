package com.backend.core.infrastructure.business.product.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.entity.product.gateway.ProductFilterRequestDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.usecase.service.CalculationService;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.RenderTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ShopController extends CrudController {
    @Autowired
    private CalculationService calculationService;

    @Autowired
    private ProductRepository productRepo;


    public ShopController(@Autowired @Qualifier("ShopCrudServiceImpl") CrudService productCrudServiceImpl) {
        super(productCrudServiceImpl);
        super.crudService = productCrudServiceImpl;
    }


    @Override
    public ResponseEntity<ApiResponse> addNewItem(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/authen/shop/rateProduct")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRenderInfoDTO request = objectMapper.readValue(json, ProductRenderInfoDTO.class);

        return crudService.updatingResponseByRequest(request, httpRequest);
    }

    @Override
    public ResponseEntity readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
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


    @Override
    @PostMapping("/unauthen/shop/allProducts")
    public ResponseEntity<ApiResponse> getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, httpRequest);
    }

    @Override
    @PostMapping("/unauthen/shop/filterProducts")
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductFilterRequestDTO productFilterRequest = objectMapper.readValue(json, ProductFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(productFilterRequest, httpRequest);
    }


    @GetMapping("/unauthen/shop/top8BestSellers")
    public ResponseEntity<ApiResponse> getTop8BestSellers(String json, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(RenderTypeEnum.TOP_8_BEST_SELL_PRODUCTS.name(), httpRequest);
    }


    @GetMapping("/unauthen/shop/newArrivalProducts")
    public ResponseEntity<ApiResponse> getNewArrivalProducts(String json, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(RenderTypeEnum.NEW_ARRIVAL_PRODUCTS.name(), httpRequest);
    }


    @GetMapping("/unauthen/shop/hotDiscountProducts")
    public ResponseEntity<ApiResponse> getHotDiscountProducts(String json, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(RenderTypeEnum.HOT_DISCOUNT_PRODUCTS.name(), httpRequest);
    }

    @GetMapping("/unauthen/shop/totalProductsQuantity")
    public ResponseEntity<ApiResponse> getTotalProductsQuantity(String json, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(RenderTypeEnum.TOTAL_PRODUCTS_QUANTITY.name(), httpRequest);
    }

    @GetMapping("/unauthen/shop/productSizeList")
    public ResponseEntity<ApiResponse> getSizeListOfProduct(@RequestParam(value = "productId") int productId, @RequestParam(value = "color") String color, HttpServletRequest httpRequest) throws IOException {
        Map<String, Object> request = new HashMap<>();

        request.put("productId", productId);
        request.put("color", color);

        return crudService.readingFromSingleRequest(request, httpRequest);
    }

    @GetMapping("/unauthen/shop/product_id={productId}")
    public ResponseEntity<ApiResponse> getProductInfo(@PathVariable(value = "productId") int productId, @RequestParam(value = "showFull") String showFull, HttpServletRequest httpRequest) throws IOException {
        Map<String, Object> request = new HashMap<>();

        request.put("productId", productId);
        request.put("showFull", showFull);

        return crudService.readingFromSingleRequest(request, httpRequest);
    }
}
