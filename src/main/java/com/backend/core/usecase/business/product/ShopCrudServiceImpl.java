package com.backend.core.usecase.business.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.entity.product.gateway.ProductFilterRequestDTO;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.category.dto.CategoryDTO;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.business.product.dto.AuthenProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.AuthenProductRenderInfoRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.service.CalculationService;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.FilterTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import usecase.util.process.ValueRenderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("ShopCrudServiceImpl")
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
    AuthenProductRenderInfoRepository authenProductRenderInfoRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    CatalogRepository catalogRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    public ShopCrudServiceImpl() {
    }


    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            // convert param object to ProductRenderInfoDTO
            ProductRenderInfoDTO request = (ProductRenderInfoDTO) paramObj;

            int productId = request.getProductId();
            int rating = request.getOverallRating();
            String color = request.getColor();

            // rating only from 1-5
            if (rating < 1 || rating > 5) {
                return new ResponseEntity<>(new ApiResponse("failed", "Rate from one to five stars only"), HttpStatus.BAD_REQUEST);
            }

            List<ProductManagement> pmList = productManagementRepo.getProductsManagementListByProductIDAndColor(productId, color);

            if (pmList.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
            } else {
                // save rating stars column in each data of product_management table
                for (ProductManagement pm : pmList) {
                    // add rating star in column
                    pm.addRatingStars(rating);
                    // get total rating star
                    pm.setTotalRatingNumber();

                    productManagementRepo.save(pm);
                }
            }

            return new ResponseEntity<>(new ApiResponse("success", "Thanks for your rating!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productRenderList = null;

        // get products from filter request
        if (paramObj instanceof ProductFilterRequestDTO productFilterRequest) {
            return filterProducts(productFilterRequest, httpRequest);
        }
        // if there is no filter -> get all products with pagination
        else if (paramObj instanceof PaginationDTO pagination) {
            return getProductFromPagination(pagination);
        }
        // get size list of product or get product details
        else if (paramObj instanceof Map requestMap) {
            List<AuthenProductRenderInfoDTO> authenProductDetails = new ArrayList<>();
            List<ProductRenderInfoDTO> productDetails = new ArrayList<>();

            try {
                int id = (int) requestMap.get("productId");
                String showFull = (String) requestMap.get("showFull");
                String color = (String) requestMap.get("color");

                List<Catalog> catalogList = catalogRepo.getCatalogsByProductId(id);
                List<CategoryDTO> categoryList = CategoryDTO.getListFromCatalogList(catalogList);

                // get product details data
                if(showFull != null && color == null) {
                    if(Boolean.valueOf(showFull) == false) {
                        productDetails = productRenderInfoRepo.getProductDetails(id);

                        for(ProductRenderInfoDTO productDetail : productDetails) {
                            productDetail.setCategories(categoryList);
                        }

                        if(productDetails.isEmpty()) {
                            return new ResponseEntity<>(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
                        }

                        return new ResponseEntity<>(new ApiResponse("success", productDetails), HttpStatus.OK);
                    }
                    else {
                        authenProductDetails = authenProductRenderInfoRepo.getAuthenProductFullDetails(id);

                        for(AuthenProductRenderInfoDTO authenProductDetail : authenProductDetails) {
                            authenProductDetail.setCategories(categoryList);
                        }

                        if(authenProductDetails.isEmpty()) {
                            return new ResponseEntity<>(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
                        }

                        return new ResponseEntity<>(new ApiResponse("success", authenProductDetails), HttpStatus.OK);
                    }
                }
                // get size list of product
                else if (showFull == null && color != null) {
                    List<String> sizeList = productManagementRepo.getSizeListByProductIdAndColor(id, color);

                    if (!sizeList.isEmpty()) {
                        return new ResponseEntity<>(new ApiResponse("success", sizeList), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
                    }
                }
                else return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        try {
            switch (renderType) {
                case "NEW_ARRIVAL_PRODUCTS": {
                    return new ResponseEntity<>(new ApiResponse("success", productRenderInfoRepo.get8NewArrivalProducts()), HttpStatus.OK);
                }
                case "HOT_DISCOUNT_PRODUCTS": {
                    return new ResponseEntity<>(new ApiResponse("success", productRenderInfoRepo.get8HotDiscountProducts()), HttpStatus.OK);
                }
                case "TOP_8_BEST_SELL_PRODUCTS": {
                    return new ResponseEntity<>(new ApiResponse("success", productRenderInfoRepo.getTop8BestSellProducts()), HttpStatus.OK);
                }
                case "TOTAL_PRODUCTS_QUANTITY": {
                    return new ResponseEntity<>(new ApiResponse("success", productRenderInfoRepo.getTotalProductsQuantity()), HttpStatus.OK);
                }
                default: {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    // filter products
    public ResponseEntity<ApiResponse> filterProducts(ProductFilterRequestDTO productFilterRequest, HttpServletRequest request) {
        try {
            String filterQuery = valueRenderUtils.getFilterQuery(productFilterRequest, FilterTypeEnum.PRODUCT, request, false);

            List<ProductRenderInfoDTO> productRenderList = customQueryRepo.getBindingFilteredList(filterQuery, ProductRenderInfoDTO.class);

            return new ResponseEntity<>(new ApiResponse("success", productRenderList), HttpStatus.OK);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // get product list from pagination
    public ResponseEntity<ApiResponse> getProductFromPagination(PaginationDTO pagination) {
        try {
            List<ProductRenderInfoDTO> productRenderList = productRenderInfoRepo.getAllProducts(
                    valueRenderUtils.getStartLineForQueryPagination(pagination.getLimit(), pagination.getPage()),
                    pagination.getLimit()
            );
            return new ResponseEntity<>(new ApiResponse("success", productRenderList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
