package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.entity.product.gateway.ProductImage;
import com.backend.core.entity.product.gateway.ProductProperty;
import com.backend.core.entity.product.key.ProductImagesManagementPrimaryKeys;
import com.backend.core.entity.product.model.Product;
import com.backend.core.entity.product.model.ProductImagesManagement;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.product.repository.ProductImagesManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AddProductPropertiesUseCase extends UseCase<AddProductPropertiesUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    ProductImagesManagementRepository productImagesManagementRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        Set<Map<String, String>> uniquePropSet = new HashSet<>();
        Set<String> uniqueColorSet = new HashSet<>();
        ProductDetailsRequestDTO request = input.getProductDetailsRequest();
        List<ProductProperty> productProperties = request.getProperties();
        List<ProductImage> productImages = request.getImages();
        Product product = productRepo.getProductById(request.getProductId());

        if (product == null) {
            return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
        }

        if (productProperties.size() != productImages.size()) {
            return new ApiResponse("failed", "Amount of product properties does not match with amount of images", HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < productProperties.size(); i++) {
            ProductProperty property = productProperties.get(i);
            ProductImage image = productImages.get(i);

            // check existed product management
            ProductManagement checkPm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                    product.getId(),
                    property.getColor(),
                    property.getSize()
            );

            if (checkPm != null) {
                return new ApiResponse("failed", String.format("%s with size %s and color %s has already existed", product.getName(), property.getSize(), property.getColor()), HttpStatus.BAD_REQUEST);
            }

            // check existed product image
            ProductImagesManagement checkPim = productImagesManagementRepo.getProductImagesByProductIdAndColor(product.getId(), image.getColor());

            if (checkPim != null) {
                return new ApiResponse("failed", String.format("Image of %s with color %s has already existed", product.getName(), property.getColor()), HttpStatus.BAD_REQUEST);
            }

            Map<String, String> map = new HashMap<>();

            map.put("size", property.getSize());
            map.put("color", property.getColor());

            // check if product property and image is unique or not
            if (uniquePropSet.add(map) && uniqueColorSet.add(image.getColor())) {
                ProductManagement pm = ProductManagement
                        .builder()
                        .product(product)
                        .size(property.getSize())
                        .color(property.getColor())
                        .availableQuantity(property.getAvailableQuantity())
                        .importDate(property.getImportDate())
                        .build();

                productManagementRepo.save(pm);

                ProductImagesManagement pim = ProductImagesManagement
                        .builder()
                        .product(product)
                        .id(new ProductImagesManagementPrimaryKeys(product.getId(), image.getColor()))
                        .build();
                pim.getImagesFromList(image.getImages());

                productImagesManagementRepo.save(pim);
            }
        }

        return new ApiResponse("success", "Add new product's property successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        ProductDetailsRequestDTO productDetailsRequest;
    }
}
