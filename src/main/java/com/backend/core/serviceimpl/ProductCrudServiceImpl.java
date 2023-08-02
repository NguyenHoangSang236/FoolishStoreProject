package com.backend.core.serviceimpl;

import com.backend.core.entities.dto.ApiResponse;
import com.backend.core.entities.dto.ListRequestDTO;
import com.backend.core.entities.dto.PaginationDTO;
import com.backend.core.entities.dto.product.ProductFilterRequestDTO;
import com.backend.core.entities.renderdto.ProductRenderInfoDTO;
import com.backend.core.entities.tableentity.ProductManagement;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.CustomQueryRepository;
import com.backend.core.repository.ProductManagementRepository;
import com.backend.core.repository.ProductRenderInfoRepository;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import com.backend.core.util.CheckUtils;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("ProductCrudServiceImpl")
public class ProductCrudServiceImpl implements CrudService {
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



    public ProductCrudServiceImpl() {}



    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse removingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse removingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse updatingResponseByList(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else {
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
                    return new ApiResponse("failed", "Rate from one to five stars only");
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

                return new ApiResponse("success", "Thanks for your rating!");
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productRenderList = null;

        // get products from filter request
        if(paramObj instanceof ProductFilterRequestDTO productFilterRequest) {
            return filterProducts(productFilterRequest, session);
        }
        // if there is no filter -> get all products with pagination
        else if(paramObj instanceof PaginationDTO pagination) {
            return getProductFromPagination(pagination);
        }
        else return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productList = new ArrayList<ProductRenderInfoDTO>();
        String status = "failed";

        try {
            switch (renderType) {
                case "NEW_ARRIVAL_PRODUCTS" -> productList = productRenderInfoRepo.get8NewArrivalProducts();
                case "HOT_DISCOUNT_PRODUCTS" -> productList = productRenderInfoRepo.get8HotDiscountProducts();
                case "TOP_8_BEST_SELL_PRODUCTS" -> productList = productRenderInfoRepo.getTop8BestSellProducts();
                default -> new ApiResponse("failed", "Wrong render type");
            }
            if(productList != null) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
            return new ApiResponse(status, ErrorTypeEnum.TECHNICAL_ERROR.name());
        }

        return new ApiResponse(status, productList);
    }


    @Override
    public ApiResponse readingById(int productId, HttpSession session, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productDetails = new ArrayList<>();
        String status = "failed";

        try {
            productDetails = productRenderInfoRepo.getProductDetails(productId);

            if(!productDetails.isEmpty()) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
        }

        return new ApiResponse(status, productDetails);
    }



    // filter products
    public ApiResponse filterProducts(ProductFilterRequestDTO productFilterRequest, HttpSession session) {
        try {
            String filterQuery = ValueRenderUtils.getFilterQuery(productFilterRequest, FilterTypeEnum.PRODUCT, session);

            List<ProductRenderInfoDTO> productRenderList = customQueryRepo.getBindingFilteredList(filterQuery, ProductRenderInfoDTO.class);

            return new ApiResponse("success", productRenderList);
        }
        catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }


    // get product list from pagination
    public ApiResponse getProductFromPagination(PaginationDTO pagination) {
        try {
            List<ProductRenderInfoDTO> productRenderList = productRenderInfoRepo.getAllProducts(
                    (pagination.getPage() - 1) * pagination.getLimit(),
                    pagination.getLimit()
            );
            return new ApiResponse("success", productRenderList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }
}
