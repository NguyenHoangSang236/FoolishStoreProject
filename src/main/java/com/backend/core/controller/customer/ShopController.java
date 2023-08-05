package com.backend.core.controller.customer;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entities.renderdto.ProductRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.product.ProductFilterRequestDTO;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public ApiResponse addNewItem(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    @PostMapping("/rateProduct")
    public ApiResponse updateItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRenderInfoDTO request = objectMapper.readValue(json, ProductRenderInfoDTO.class);

        return crudService.updatingResponseByRequest(request, session, httpRequest);
    }

    @Override
    @GetMapping("/product_id={productId}")
    public ApiResponse readSelectedItemById(@PathVariable(value = "productId") int productId, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingById(productId, session, httpRequest);
    }


    @Override
    public ApiResponse deleteSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @Override
    public ApiResponse updateSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }


    @Override
    @PostMapping("/allProducts")
    public ApiResponse getListOfItems(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationDTO pagination = objectMapper.readValue(json, PaginationDTO.class);
        return crudService.readingFromSingleRequest(pagination, session, httpRequest);
    }

    @Override
    @PostMapping("/filterProducts")
    public ApiResponse getListOfItemsFromFilter(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductFilterRequestDTO productFilterRequest = objectMapper.readValue(json, ProductFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(productFilterRequest, session, httpRequest);
    }


    @GetMapping("/top8BestSellers")
    public ApiResponse getTop8BestSellers(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.TOP_8_BEST_SELL_PRODUCTS.name(), httpRequest);
    }


    @GetMapping("/newArrivalProducts")
    public ApiResponse getNewArrivalProducts(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.NEW_ARRIVAL_PRODUCTS.name(), httpRequest);
    }


    @GetMapping("/hotDiscountProducts")
    public ApiResponse getHotDiscountProducts(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.readingResponse(session, RenderTypeEnum.HOT_DISCOUNT_PRODUCTS.name(), httpRequest);
    }
}
