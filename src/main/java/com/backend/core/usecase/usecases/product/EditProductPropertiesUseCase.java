package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.entity.product.gateway.ProductImage;
import com.backend.core.entity.product.gateway.ProductProperty;
import com.backend.core.entity.product.model.Product;
import com.backend.core.entity.product.model.ProductImagesManagement;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.product.repository.ProductImagesManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EditProductPropertiesUseCase extends UseCase<EditProductPropertiesUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    ProductImagesManagementRepository productImagesManagementRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        ProductDetailsRequestDTO productDetailsRequest = input.getProductDetailsRequest();
        int productId = productDetailsRequest.getProductId();
        List<ProductProperty> propertyList = productDetailsRequest.getProperties();
        List<ProductImage> imageList = productDetailsRequest.getImages();

        // check product id is valid or not
        if (productId == 0) {
            return new ApiResponse("failed", "Please input product id", HttpStatus.BAD_REQUEST);
        }
        // check product properties is null or not
        else if (propertyList == null || propertyList.isEmpty()) {
            return new ApiResponse("failed", "Please input product properties", HttpStatus.BAD_REQUEST);
        }
        // proceed editing
        else {
            Product product = productRepo.getProductById(productId);

            if (product == null) {
                return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
            }

            List<ProductManagement> pmList = product.getProductManagements();

            // check size of product properties list and product management list are equal or not
            if (pmList.size() < propertyList.size()) {
                return new ApiResponse("failed", "Number of properties from request is larger than number of product's properties", HttpStatus.BAD_REQUEST);
            }

            // save every product property
            for (int i = 0; i < propertyList.size(); i++) {
                ProductProperty prop = propertyList.get(i);

                ProductManagement pm = productManagementRepo.getProductManagementById(prop.getId());

                if (pm == null) {
                    return new ApiResponse("failed", "Product property ID does not belong to this product", HttpStatus.BAD_REQUEST);
                } else {
                    pm.setAvailableQuantity(prop.getAvailableQuantity());
                    Optional.ofNullable(prop.getImportDate()).ifPresent(pm::setImportDate);

                    productManagementRepo.save(pm);
                }
            }

            // save product images
            if (imageList != null) {
                for (ProductImage image : imageList) {
                    ProductImagesManagement pim = productImagesManagementRepo.getProductImagesByProductIdAndColor(productId, image.getColor());

                    if (pim == null) {
                        return new ApiResponse("failed", "This product does not have " + image.getColor() + " color, please check again", HttpStatus.BAD_REQUEST);
                    }

                    pim.getImagesFromList(image.getImages());

                    productImagesManagementRepo.save(pim);
                }
            }

            return new ApiResponse("success", "Edit product's properties successfully", HttpStatus.OK);
        }
    }

    @Value
    public static class InputValue implements UseCase.InputValues {
        ProductDetailsRequestDTO productDetailsRequest;
    }
}
