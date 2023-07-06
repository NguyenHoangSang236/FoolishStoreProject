package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.*;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.CustomQueryRepository;
import com.backend.core.repository.ProductRenderInfoRepository;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ApiResponse removingResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse updatingResponse(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        List<ProductRenderInfoDTO> productRenderList = null;

        if(paramObj instanceof ProductFilterRequestDTO) {
            try {
                String filterQuery = ValueRenderUtils.getFilterQuery(paramObj, FilterTypeEnum.PRODUCT, session);

                productRenderList = customQueryRepo.getBindingFilteredList(filterQuery, ProductRenderInfoDTO.class);
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
        // if there is no filter -> get all products with pagination
        else if(paramObj instanceof PaginationDTO) {
            try {
                PaginationDTO pagination = (PaginationDTO) paramObj;

                productRenderList = productRenderInfoRepo.getAllProducts(
                        (pagination.getPage() - 1) * pagination.getLimit(),
                        pagination.getLimit()
                );
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }

        return new ApiResponse("success", productRenderList);
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

            if(productDetails.size() > 0) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
        }

        return new ApiResponse(status, productDetails);
    }
}
