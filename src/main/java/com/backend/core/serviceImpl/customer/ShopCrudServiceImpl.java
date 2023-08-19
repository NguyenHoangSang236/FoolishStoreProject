package com.backend.core.serviceImpl.customer;

import com.backend.core.entities.renderdto.ProductRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.product.ProductFilterRequestDTO;
import com.backend.core.entities.tableentity.ProductManagement;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.repository.product.ProductRenderInfoRepository;
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("ProductCrudServiceImpl")
public class ShopCrudServiceImpl implements CrudService {
    @Autowired
    CalculationService calculationService;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    public ShopCrudServiceImpl() {
    }


    @Override
    public ResponseEntity singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            // convert param object to ProductRenderInfoDTO
            ProductRenderInfoDTO request = (ProductRenderInfoDTO) paramObj;

            int productId = request.getProductId();
            int rating = request.getOverallRating();
            String color = request.getColor();

            System.out.println(productId);
            System.out.println(rating);
            System.out.println(color);

            // rating only from 1-5
            if (rating < 1 || rating > 5) {
                return new ResponseEntity(new ApiResponse("failed", "Rate from one to five stars only"), HttpStatus.BAD_REQUEST);
            }

            List<ProductManagement> pmList = productManagementRepo.getProductsManagementListByProductIDAndColor(productId, color);

            // save rating stars column in each data of product_management table
            for (ProductManagement pm : pmList) {
                // add rating star in column
                pm.addRatingStars(rating);
                // get total rating star
                pm.setTotalRatingNumber();

                productManagementRepo.save(pm);
            }

            return new ResponseEntity(new ApiResponse("success", "Thanks for your rating!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productRenderList = null;

        // get products from filter request
        if (paramObj instanceof ProductFilterRequestDTO productFilterRequest) {
            return filterProducts(productFilterRequest, httpRequest);
        }
        // if there is no filter -> get all products with pagination
        else if (paramObj instanceof PaginationDTO pagination) {
            return getProductFromPagination(pagination);
        } else
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity readingResponse(String renderType, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productList = new ArrayList<ProductRenderInfoDTO>();
        String status = "failed";

        try {
            switch (renderType) {
                case "NEW_ARRIVAL_PRODUCTS" -> productList = productRenderInfoRepo.get8NewArrivalProducts();
                case "HOT_DISCOUNT_PRODUCTS" -> productList = productRenderInfoRepo.get8HotDiscountProducts();
                case "TOP_8_BEST_SELL_PRODUCTS" -> productList = productRenderInfoRepo.getTop8BestSellProducts();
                default -> new ApiResponse("failed", "Wrong render type");
            }
            if (productList != null) {
                status = "success";
            }
        } catch (Exception e) {
            status = "failed";
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(status, ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(new ApiResponse(status, productList), HttpStatus.OK);
    }


    @Override
    public ResponseEntity readingById(int productId, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productDetails = new ArrayList<>();

        try {
            productDetails = productRenderInfoRepo.getProductDetails(productId);

            if (!productDetails.isEmpty()) {
                return new ResponseEntity(new ApiResponse("success", productDetails), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.OK);
        }
    }


    // filter products
    public ResponseEntity filterProducts(ProductFilterRequestDTO productFilterRequest, HttpServletRequest request) {
        try {
            String filterQuery = valueRenderUtils.getFilterQuery(productFilterRequest, FilterTypeEnum.PRODUCT, request);

            List<ProductRenderInfoDTO> productRenderList = customQueryRepo.getBindingFilteredList(filterQuery, ProductRenderInfoDTO.class);

            return new ResponseEntity(new ApiResponse("success", productRenderList), HttpStatus.OK);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // get product list from pagination
    public ResponseEntity getProductFromPagination(PaginationDTO pagination) {
        try {
            List<ProductRenderInfoDTO> productRenderList = productRenderInfoRepo.getAllProducts(
                    valueRenderUtils.getStartLineForQueryPagination(pagination.getLimit(), pagination.getPage()),
                    pagination.getLimit()
            );
            return new ResponseEntity(new ApiResponse("success", productRenderList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
