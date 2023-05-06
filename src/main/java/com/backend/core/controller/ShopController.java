package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.entity.dto.PaginationDTO;
import com.backend.core.entity.dto.ProductFilterRequestDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
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
@RequestMapping(value = "/shop", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShopController extends CrudController {
    @Autowired
    private CalculationService calculationService;

    @Autowired
    private ProductRepository productRepo;



    public ShopController(@Autowired @Qualifier("ProductCrudServiceImpl") CrudService productCrudServiceImpl) {
        super(productCrudServiceImpl);
        super.crudService = productCrudServiceImpl;
    }


    @Override
    public ApiResponse addNewItem(String json, HttpSession session) throws IOException {
        return null;
    }


    @Override
    public ApiResponse updateItem(String json, HttpSession session) throws IOException {
        return null;
    }

    @Override
    @GetMapping("/product_id={productId}")
    public ApiResponse readSelectedItemById(@PathVariable(value = "productId") int productId, HttpSession session) throws IOException {
        return crudService.readingById(productId, session);
    }


    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }

    @Override
    public ApiResponse updateSelectedItemById(int id, HttpSession session) throws IOException {
        return null;
    }


    @Override
    @PostMapping("/allProducts")
    public ApiResponse getListOfItems(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, session);
    }

    @Override
    @PostMapping("/filterProducts")
    public ApiResponse getListOfItemsFromFilter(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductFilterRequestDTO productFilterRequest = objectMapper.readValue(json, ProductFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(productFilterRequest, session);
    }


    @GetMapping("/top8BestSellers")
    public ApiResponse getTop8BestSellers(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.TOP_8_BEST_SELL_PRODUCTS.name());
    }


    @GetMapping("/newArrivalProducts")
    public ApiResponse getNewArrivalProducts(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.NEW_ARRIVAL_PRODUCTS.name());
    }


    @GetMapping("/hotDiscountProducts")
    public ApiResponse getHotDiscountProducts(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.HOT_DISCOUNT_PRODUCTS.name());
    }
}
