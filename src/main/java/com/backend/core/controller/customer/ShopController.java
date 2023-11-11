package com.backend.core.controller.customer;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.product.ProductFilterRequestDTO;
import com.backend.core.entities.responsedto.ProductRenderInfoDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//@PreAuthorize("hasAuthority('CUSTOMER')")
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
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
    @GetMapping("/unauthen/shop/product_id={productId}")
    public ResponseEntity<ApiResponse> readSelectedItemById(@PathVariable(value = "productId") int productId, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingById(productId, httpRequest);
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
}
