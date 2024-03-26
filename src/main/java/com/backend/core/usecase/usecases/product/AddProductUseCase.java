package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.entity.product.gateway.ProductImage;
import com.backend.core.entity.product.gateway.ProductProperty;
import com.backend.core.entity.product.key.ProductImagesManagementPrimaryKeys;
import com.backend.core.entity.product.model.Product;
import com.backend.core.entity.product.model.ProductImagesManagement;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.business.product.repository.ProductImagesManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AddProductUseCase extends UseCase<AddProductUseCase.InputValue, ApiResponse> {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    CustomQueryRepository customQueryRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    ProductImagesManagementRepository productImagesManagementRepo;
    @Autowired
    CatalogRepository catalogRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        ProductDetailsRequestDTO request = input.getProductDetailsRequest();
        String unqualifiedRequestMsg = messageForUnqualifiedAddingRequest(request);

        if (unqualifiedRequestMsg != null) {
            return new ApiResponse("failed", unqualifiedRequestMsg, HttpStatus.BAD_REQUEST);
        } else
            return saveProductProcess(request);
    }

    public ApiResponse saveProductProcess(ProductDetailsRequestDTO request) {
        // save product first
        Product product = new Product();
        List<Catalog> categoryList = new ArrayList<>();
        Product existedProduct = productRepo.getProductByFullName(request.getName().toLowerCase());

        // if this product is existed and the API is for adding -> error
        if (existedProduct != null) {
            return new ApiResponse("failed", "This product has been existed", HttpStatus.BAD_REQUEST);
        }

        if (request.getImages().size() != request.getProperties().size()) {
            return new ApiResponse("failed", "Amount of product properties does not match with amount of images", HttpStatus.BAD_REQUEST);
        }

        // build Product from ProductAddingRequestDTO
        product.getProductFromProductDetailsRequest(request);
        product.setCatalogs(categoryList);

        productRepo.save(product);

        // save product management, catalogs_with_products and product images later
        return saveOtherTables(request, product);
    }

    public ApiResponse saveOtherTables(ProductDetailsRequestDTO request, Product product) {
        // save categories
        for (Integer cateId : request.getCategoryIds()) {
            Catalog category = catalogRepo.getCatalogById(cateId);

            if (category == null) {
                return new ApiResponse("failed", "Category does not exist", HttpStatus.BAD_REQUEST);
            }

            customQueryRepo.insertCatalogWithProducts(cateId, product.getId());
        }

        // save product properties and images
        saveNewProductManagement(request.getProperties(), product);
        saveProductImagesManagement(request.getImages(), product);

        return new ApiResponse("success", "Add new product successfully", HttpStatus.OK);
    }

    // check if product adding request is qualified to proceed
    public String messageForUnqualifiedAddingRequest(ProductDetailsRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return "Please input product's name";
        } else if (request.getOriginalPrice() < 1 || request.getSellingPrice() < 1) {
            return "Prices must be higher than 0";
        } else if (request.getBrand() == null || request.getBrand().isBlank()) {
            return "Please input product's brand";
        } else if (request.getProperties() == null || request.getProperties().isEmpty()) {
            return "Please input product's features or attributes";
        } else return null;
    }

    // save new data to product management and product import management tables
    public void saveNewProductManagement(List<ProductProperty> properties, Product product) {
        for (ProductProperty property : properties) {
            try {
                ProductManagement pm = ProductManagement
                        .builder()
                        .color(property.getColor())
                        .availableQuantity(property.getAvailableQuantity())
                        .size(property.getSize())
                        .product(product)
                        .importDate(new Date())
                        .build();

                productManagementRepo.save(pm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // save new data to product images management table
    public void saveProductImagesManagement(List<ProductImage> productImages, Product product) {
        try {
            if (productImages != null) {
                for (ProductImage img : productImages) {
                    ProductImagesManagement pim = ProductImagesManagement
                            .builder()
                            .product(product)
                            .id(new ProductImagesManagementPrimaryKeys(product.getId(), img.getColor()))
                            .build();
                    pim.getImagesFromList(img.getImages());
                    pim.getImagesFromList(img.getImages());

                    productImagesManagementRepo.save(pim);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value
    public static class InputValue implements InputValues {
        ProductDetailsRequestDTO productDetailsRequest;
    }
}
