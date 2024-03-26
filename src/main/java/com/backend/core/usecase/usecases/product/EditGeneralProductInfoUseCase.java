package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.entity.product.model.Product;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EditGeneralProductInfoUseCase extends UseCase<EditGeneralProductInfoUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    CatalogRepository catalogRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        ProductDetailsRequestDTO productDetailsRequest = input.getProductDetailsRequest();
        String unqualifiedRequestMsg = messageForUnqualifiedAddingRequest(productDetailsRequest);
        List<Catalog> catalogList = new ArrayList<>();

        if (unqualifiedRequestMsg != null) {
            return new ApiResponse("failed", unqualifiedRequestMsg, HttpStatus.BAD_REQUEST);
        } else {
            Product selectedProduct = productRepo.getProductById(productDetailsRequest.getProductId());

            for (int cateId : productDetailsRequest.getCategoryIds()) {
                Catalog catalog = catalogRepo.getCatalogById(cateId);

                if (catalog != null) {
                    catalogList.add(catalog);
                } else
                    return new ApiResponse("failed", "Category with id = " + cateId + " does not exist", HttpStatus.BAD_REQUEST);
            }

            if (selectedProduct == null) {
                return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
            } else {
                selectedProduct.setName(productDetailsRequest.getName());
                selectedProduct.setBrand(productDetailsRequest.getBrand());
                selectedProduct.setDescription(productDetailsRequest.getDescription());
                selectedProduct.setDiscount(productDetailsRequest.getDiscount());
                selectedProduct.setOriginalPrice(productDetailsRequest.getOriginalPrice());
                selectedProduct.setSellingPrice(productDetailsRequest.getSellingPrice());
                selectedProduct.setHeight(productDetailsRequest.getHeight());
                selectedProduct.setLength(productDetailsRequest.getLength());
                selectedProduct.setWidth(productDetailsRequest.getWidth());
                selectedProduct.setWeight(productDetailsRequest.getWeight());
                selectedProduct.setCatalogs(catalogList);

                productRepo.save(selectedProduct);

                return new ApiResponse("success", "Edit general information of product successfully", HttpStatus.OK);
            }
        }
    }

    // check if product adding request is qualified to proceed
    public String messageForUnqualifiedAddingRequest(ProductDetailsRequestDTO product) {
        if (product.getProductId() <= 0) {
            return "Please input product's id";
        } else if (product.getName() == null || product.getName().isBlank()) {
            return "Please input product's name";
        } else if (product.getOriginalPrice() > product.getSellingPrice()) {
            return "Original price can not be higher than selling price";
        } else if (product.getBrand() == null || product.getBrand().isBlank()) {
            return "Please input product's brand";
        } else if (product.getCategoryIds() == null || product.getCategoryIds().isEmpty()) {
            return "Please input category ids of product";
        } else return null;
    }

    @Value
    public static class InputValue implements InputValues {
        ProductDetailsRequestDTO productDetailsRequest;
    }
}
