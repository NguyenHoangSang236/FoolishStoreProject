package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.FilterFactory;
import com.backend.core.entity.dto.ProductFilterDTO;
import com.backend.core.entity.dto.ProductFilterRequestDTO;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import com.backend.core.entity.tableentity.Product;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.CustomQueryRepository;
import com.backend.core.repository.ProductRenderInfoRepository;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ApiResponse creationalResponse(Object paramObj, HttpSession session) {
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

        try {
            FilterRequest productFilterRequest = FilterFactory.getFilterRequest(FilterTypeEnum.PRODUCT);
            productFilterRequest = (ProductFilterRequestDTO) paramObj;

            ProductFilterDTO productFilter = (ProductFilterDTO) productFilterRequest.getFilter();

            // product filter does not have Name filed -> binding filter
            if(productFilter.getName().isEmpty() || productFilter.getName().isBlank()) {
                String filterQuery = ValueRenderUtils.createQueryForProductFilter(
                        productFilter.getCategories(),
                        productFilter.getBrand(),
                        productFilter.getMinPrice(),
                        productFilter.getMaxPrice()
                );

                // get object[] list from query
                List<Object[]> objectList = customQueryRepo.getBindingFilteredList(filterQuery);

                // convert object[] list to ProductRenderInfoDTO list
                productRenderList = objectList.stream()
                        .map(
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
                productRenderList = productRenderInfoRepo.getProductsByName(productFilter.getName());
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();

            return new ApiResponse("failed", "No data for filter, please select data!");
        } catch (Exception e) {
            e.printStackTrace();

            return new ApiResponse("failed", e.toString());
        }

        return new ApiResponse("success", productRenderList);
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, RenderTypeEnum renderType) {
        List<ProductRenderInfoDTO> allProductsList = new ArrayList<ProductRenderInfoDTO>();
        String status = "failed";

        try {
            switch (renderType) {
                case ALL_PRODUCTS -> allProductsList = productRenderInfoRepo.getAllProducts();
                case NEW_ARRIVAL_PRODUCTS -> allProductsList = productRenderInfoRepo.get8NewArrivalProducts();
                case HOT_DISCOUNT_PRODUCTS -> allProductsList = productRenderInfoRepo.get8HotDiscountProducts();
                case TOP_8_BEST_SELL_PRODUCTS -> allProductsList = productRenderInfoRepo.getTop8BestSellProducts();
                default -> new ApiResponse("failed", "Wrong RenderType");
            }
            if(allProductsList != null) {
//                System.out.println(allProductsList.get(0).calculation(calculationService));
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
        }

        return new ApiResponse(status, allProductsList);
    }


    @Override
    public ApiResponse readingById(int id, HttpSession session) {
        Product product = new Product();
        String status = "failed";

        try {
            product = productRepo.getProductById(id);

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
