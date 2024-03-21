package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.dto.CategoryDTO;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.business.product.dto.AuthenProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.dto.ProductRenderInfoDTO;
import com.backend.core.infrastructure.business.product.repository.AuthenProductRenderInfoRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ViewProductByIdUseCase extends UseCase<ViewProductByIdUseCase.InputValue, ApiResponse> {
    @Autowired
    CatalogRepository catalogRepo;
    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;
    @Autowired
    AuthenProductRenderInfoRepository authenProductRenderInfoRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        try {
            int id = input.getProductId();
            boolean isAuthen = input.isAuthen();

            List<Catalog> catalogList = catalogRepo.getCatalogsByProductId(id);
            List<CategoryDTO> categoryList = CategoryDTO.getListFromCatalogList(catalogList);

            // show full info of product
            if(isAuthen) {
                List<AuthenProductRenderInfoDTO> authenProductDetails = new ArrayList<>();
                authenProductDetails = authenProductRenderInfoRepo.getAuthenProductFullDetails(id);

                for (AuthenProductRenderInfoDTO authenProductDetail : authenProductDetails) {
                    authenProductDetail.setCategories(categoryList);
                }

                if (authenProductDetails.isEmpty()) {
                    return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
                }

                return new ApiResponse("success", authenProductDetails, HttpStatus.OK);
            }
            // show general info of product
            else {
                List<ProductRenderInfoDTO> productDetails = new ArrayList<>();
                productDetails = productRenderInfoRepo.getProductDetails(id);

                for (ProductRenderInfoDTO productDetail : productDetails) {
                    productDetail.setCategories(categoryList);
                }

                if (productDetails.isEmpty()) {
                    return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
                }

                return new ApiResponse("success", productDetails, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues  {
        int productId;
        boolean isAuthen;
    }
}
