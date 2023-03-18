package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import com.backend.core.entity.tableentity.Product;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.ProductRenderInfoRepository;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.CalculationService;
import com.backend.core.service.CrudService;
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
        FilterRequest filterRequest = (FilterRequest) paramObj;



        return null;
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
