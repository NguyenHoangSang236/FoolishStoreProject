package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/shop")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShopController extends CrudController {
    @Autowired
    private CalculationService calculationService;

    @Autowired
    private ProductRepository productRepo;



    public ShopController(@Autowired @Qualifier("ProductCrudServiceImpl") CrudService crudService) {
        super(crudService);
        super.crudService = crudService;
    }


    @Override
    public ApiResponse addNewItem(String json, HttpSession session) throws IOException {
        return null;
    }

    @Override
    public ApiResponse updateItem(String json, HttpSession session) throws IOException {
        return null;
    }

    @GetMapping("/allProducts")
    @Override
    public ApiResponse getListOfItems(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.ALL_PRODUCTS);
    }

    @GetMapping("/top8BestSellers")
    public ApiResponse getTop8BestSellers(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.TOP_8_BEST_SELL_PRODUCTS);
    }

    @GetMapping("/newArrivalProducts")
    public ApiResponse getNewArrivalProducts(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.NEW_ARRIVAL_PRODUCTS);
    }

    @GetMapping("/hotDiscountProducts")
    public ApiResponse getHotDiscountProducts(String json, HttpSession session) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.HOT_DISCOUNT_PRODUCTS);
    }


    @GetMapping("/product_id={productId}")
    public ApiResponse getProductById(@PathVariable(value = "productId") int productId, HttpSession session) {
        return crudService.readingById(productId, session);
    }


    @PostMapping("/searchProductByName")
    public ApiResponse getProductByName() {
        return new ApiResponse("status", "product");
    }
}
