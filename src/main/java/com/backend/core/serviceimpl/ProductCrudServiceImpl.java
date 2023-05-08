package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.*;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import com.backend.core.entity.tableentity.Product;
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
    CustomQueryRepository customQueryRepo;



    public ProductCrudServiceImpl() {}



    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session) {
        return null;
    }

    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse removingResponse(Object paramObj, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse updatingResponse(List<Object> paramObjList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session) {
        List<ProductRenderInfoDTO> productRenderList = null;

        if(paramObj instanceof ProductFilterRequestDTO) {
            try {
                // determine filter type
                FilterRequest productFilterRequest = FilterFactory.getFilterRequest(FilterTypeEnum.PRODUCT);

                // convert paramObj
                productFilterRequest = (ProductFilterRequestDTO) paramObj;

                ProductFilterDTO productFilter = (ProductFilterDTO) productFilterRequest.getFilter();

                // product filter does not have Name filed -> binding filter
                if(productFilter.getName() == null || productFilter.getName().isBlank()) {
                    // get filter query
                    String filterQuery = ValueRenderUtils.productFilterQuery(
                            productFilter.getCategories(),
                            productFilter.getBrand(),
                            productFilter.getMinPrice(),
                            productFilter.getMaxPrice(),
                            productFilterRequest.getPagination().getPage(),
                            productFilterRequest.getPagination().getLimit()
                    );

                    // get object[] list from query
                    List<Object[]> objectList = customQueryRepo.getBindingFilteredList(filterQuery);

                    // convert object[] list to ProductRenderInfoDTO list
                    productRenderList = objectList.stream().map(
                            obj -> new ProductRenderInfoDTO(
                                    obj[0] instanceof Long ? ((Long) obj[0]).intValue() : (int) obj[0],
                                    obj[1] instanceof Long ? ((Long) obj[1]).intValue() : (int) obj[1],
                                    (String) obj[2],
                                    (String) obj[3],
                                    (double) obj[4],
                                    (double) obj[5],
                                    (String) obj[6],
                                    (String) obj[7],
                                    obj[8] instanceof Long ? ((Long) obj[8]).intValue() : (int) obj[8],
                                    (String) obj[9],
                                    (String) obj[10],
                                    (String) obj[11],
                                    (String) obj[12]
                            )
                    ).toList();
                }
                else {
                    productRenderList = productRenderInfoRepo.getProductsByName(
                            productFilter.getName(),
                            (productFilterRequest.getPagination().getPage() - 1) * productFilterRequest.getPagination().getLimit(),
                            productFilterRequest.getPagination().getLimit()
                    );
                }
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
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType) {
        List<ProductRenderInfoDTO> allProductsList = new ArrayList<ProductRenderInfoDTO>();
        String status = "failed";

        try {
            switch (renderType) {
                case "NEW_ARRIVAL_PRODUCTS" -> allProductsList = productRenderInfoRepo.get8NewArrivalProducts();
                case "HOT_DISCOUNT_PRODUCTS" -> allProductsList = productRenderInfoRepo.get8HotDiscountProducts();
                case "TOP_8_BEST_SELL_PRODUCTS" -> allProductsList = productRenderInfoRepo.getTop8BestSellProducts();
                default -> new ApiResponse("failed", "Wrong render type");
            }
            if(allProductsList != null) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
            return new ApiResponse(status, ErrorTypeEnum.TECHNICAL_ERROR.name());
        }

        return new ApiResponse(status, allProductsList);
    }


    @Override
    public ApiResponse readingById(int productId, HttpSession session) {
        Product product = new Product();
        String status = "failed";

        try {
            product = productRepo.getProductById(productId);

            if(product.getName() != null) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
        }

        return new ApiResponse(status, product);
    }
}
