package com.backend.core.usecase.usecases.product;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.entity.product.gateway.ProductAttribute;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.entity.product.gateway.ProductSizeQuantity;
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
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        try {
            ProductDetailsRequestDTO request = input.getProductDetailsRequest();
            String unqualifiedRequestMsg = messageForUnqualifiedAddingRequest(request);

            if (unqualifiedRequestMsg != null) {
                return new ApiResponse("failed", unqualifiedRequestMsg, HttpStatus.BAD_REQUEST);
            } else
                return saveProductProcess(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse saveProductProcess(ProductDetailsRequestDTO request) {
        try {
            // save product first
            Product product = new Product();
            List<Catalog> categoryList = new ArrayList<>();
            Product existedProduct = productRepo.getProductByFullName(request.getName().toLowerCase());

            // if this product is existed and the API is for adding -> error
            if (existedProduct != null) {
                return new ApiResponse("failed", "This product has been existed", HttpStatus.BAD_REQUEST);
            }

            // build Product from ProductAddingRequestDTO
            product.getProductFromProductDetailsRequest(request);
            product.setCatalogs(categoryList);

            // save product management, catalogs_with_products and product images later
            return saveOtherTables(request, product);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse saveOtherTables(ProductDetailsRequestDTO request, Product product) {
        try {
            List<ProductAttribute> attributes = request.getAttributes();
            String unqualifiedAttrMsg = messageForUnqualifiedAttribute(attributes);

            // check if every attribute is qualified to proceed
            if (unqualifiedAttrMsg != null) {
                return new ApiResponse("failed", unqualifiedAttrMsg, HttpStatus.BAD_REQUEST);
            } else {
                productRepo.save(product);

                for (Integer cateId : request.getCategoryIds()) {
                    Catalog category = catalogRepo.getCatalogById(cateId);

                    if (category == null) {
                        return new ApiResponse("failed", "Category does not exist", HttpStatus.BAD_REQUEST);
                    }

                    customQueryRepo.insertCatalogWithProducts(cateId, product.getId());
                }


                for (ProductAttribute attribute : attributes) {
                    saveNewProductManagement(attribute, product);
                    saveProductImagesManagement(attribute, product);
                }

                return new ApiResponse("success", "Add new product successfully", HttpStatus.OK);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // check if every attribute is qualified to keep proceeding
    public String messageForUnqualifiedAttribute(List<ProductAttribute> attributes) {
        for (ProductAttribute attribute : attributes) {
            if (attribute.getSizeQuantities() == null || attribute.getSizeQuantities().isEmpty()) {
                return "Each size of product's attribute must have sizes and their quantity";
            } else if (attribute.getImages() == null || attribute.getImages().length < 1 || attribute.getImages().length > 4) {
                return "Product's attribute must have 1 image at least and 4 images at most";
            } else if (attribute.getColor() == null || attribute.getColor().isBlank()) {
                return "Product's attribute must have color";
            }
        }

        return null;
    }

    // check if product adding request is qualified to proceed
    public String messageForUnqualifiedAddingRequest(ProductDetailsRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return "Please input product's name";
        } else if (request.getOriginalPrice() < 1 || request.getSellingPrice() < 1) {
            return "Prices must be higher than 0";
        } else if (request.getBrand() == null || request.getBrand().isBlank()) {
            return "Please input product's brand";
        } else if (request.getAttributes() == null || request.getAttributes().isEmpty()) {
            return "Please input product's features or attributes";
        } else return null;
    }

    // save new data to product management and product import management tables
    public void saveNewProductManagement(ProductAttribute attribute, Product product) {
        try {
            for (ProductSizeQuantity sizeQuant : attribute.getSizeQuantities()) {
                ProductManagement pm = ProductManagement
                        .builder()
                        .color(attribute.getColor())
                        .availableQuantity(sizeQuant.getQuantity())
                        .size(sizeQuant.getSize())
                        .product(product)
                        .build();

                pm.setColor(attribute.getColor());
                pm.setSize(sizeQuant.getSize());
                pm.setAvailableQuantity(sizeQuant.getQuantity());
                pm.setProduct(product);

                productManagementRepo.save(pm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // save new data to product images management table
    public void saveProductImagesManagement(ProductAttribute attribute, Product product) {
        try {
            ProductImagesManagementPrimaryKeys pimPk = new ProductImagesManagementPrimaryKeys();
            ProductImagesManagement productImgMng = new ProductImagesManagement();

            pimPk.setProductId(product.getId());
            pimPk.setColor(attribute.getColor());

            productImgMng.setProduct(product);
            productImgMng.setId(pimPk);
            productImgMng.getImagesFromList(attribute.getImages());

            productImagesManagementRepo.save(productImgMng);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value
    public static class InputValue implements InputValues {
        ProductDetailsRequestDTO productDetailsRequest;
    }
}
