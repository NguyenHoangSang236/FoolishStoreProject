package com.backend.core.serviceImpl.admin;

import com.backend.core.entities.embededkey.ProductImagesManagementPrimaryKeys;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.product.ProductAddingRequestDTO;
import com.backend.core.entities.requestdto.product.ProductAttribute;
import com.backend.core.entities.tableentity.Product;
import com.backend.core.entities.tableentity.ProductImagesManagement;
import com.backend.core.entities.tableentity.ProductImportManagement;
import com.backend.core.entities.tableentity.ProductManagement;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.product.ProductImagesManagementRepository;
import com.backend.core.repository.product.ProductImportManagementRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

@Service
@Qualifier("ProductCrudServiceImpl")
public class ProductCrudServiceImpl implements CrudService {
    @Autowired
    ProductRepository productRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;

    @Autowired
    ProductImagesManagementRepository productImagesManagementRepo;
    @Autowired
    private ProductImportManagementRepository productImportManagementRepo;


    @Override
    public ResponseEntity singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        BindingResult bindingResult = (BindingResult) httpRequest.getAttribute("org.springframework.validation.BindingResult.myRequest");

        try {
            ProductAddingRequestDTO request = (ProductAddingRequestDTO) paramObj;

            String unqualifiedRequestMsg = messageForUnqualifiedAddingRequest(request);

            if (unqualifiedRequestMsg != null) {
                return new ResponseEntity(new ApiResponse("failed", unqualifiedRequestMsg), HttpStatus.BAD_REQUEST);
            } else
                return saveNewProductProcess(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    // save new product process
    public ResponseEntity saveNewProductProcess(ProductAddingRequestDTO request) {
        // todo: save product first
        Product newProduct = new Product();
        Product existedProduct = productRepo.getProductByFullName(request.getName().toLowerCase());

        // if this product is existed -> error
        if (existedProduct != null) {
            return new ResponseEntity(new ApiResponse("failed", "This product has been existed"), HttpStatus.BAD_REQUEST);
        }

        int newProductId = productRepo.getLatestProductId() + 1;

        // build Product from ProductAddingRequestDTO
        newProduct.getProductFromProductAddingRequest(request);
        newProduct.setId(newProductId);

        // todo: save product management, product import and product images later
        return saveOtherTables(request, newProduct);
    }


    public ResponseEntity saveOtherTables(ProductAddingRequestDTO request, Product product) {
        List<ProductAttribute> attributes = request.getAttributes();
        String unqualifiedAttrMsg = messageForUnqualifiedAttribute(attributes);

        // check if every attribute is qualified to proceed
        if (unqualifiedAttrMsg != null) {
            return new ResponseEntity(new ApiResponse("failed", unqualifiedAttrMsg), HttpStatus.BAD_REQUEST);
        } else {
            productRepo.save(product);

            for (ProductAttribute attribute : attributes) {
                saveProductImagesManagement(attribute, product);
                saveProductManagementAndImportManagement(attribute, request.getImportDate(), product);
            }

            return new ResponseEntity(new ApiResponse("success", "Add new product successfully"), HttpStatus.OK);
        }
    }


    // save data to ProductImagesManagement table
    public void saveProductImagesManagement(ProductAttribute attribute, Product product) {
        try {
            ProductImagesManagementPrimaryKeys pimPk = new ProductImagesManagementPrimaryKeys();

            pimPk.setProductId(product.getId());
            pimPk.setColor(attribute.getColor());

            ProductImagesManagement productImgMng = new ProductImagesManagement();

            productImgMng.setProduct(product);
            productImgMng.setId(pimPk);
            productImgMng.getImagesFromList(attribute.getImages());

            productImagesManagementRepo.save(productImgMng);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // save data to product management and product import management tables
    public void saveProductManagementAndImportManagement(ProductAttribute attribute, Date importDate, Product product) {
        try {
            for (int i = 0; i < attribute.getSizes().length; i++) {
                ProductManagement pm = new ProductManagement();

                pm.setColor(attribute.getColor());
                pm.setSize(attribute.getSizes()[i]);
                pm.setAvailableQuantity(attribute.getQuantity()[i]);
                pm.setProduct(product);

                productManagementRepo.save(pm);

                pm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                        product.getId(),
                        attribute.getColor(),
                        attribute.getSizes()[i]
                );

                ProductImportManagement pim = new ProductImportManagement(
                        attribute.getQuantity()[i],
                        pm,
                        importDate
                );

                productImportManagementRepo.save(pim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // check if every attribute is qualified to keep proceeding
    public String messageForUnqualifiedAttribute(List<ProductAttribute> attributes) {
        for (ProductAttribute attribute : attributes) {
            if (attribute.getSizes() == null || attribute.getQuantity() == null || attribute.getSizes().length != attribute.getQuantity().length) {
                return "Each size of product's attribute must have quantity";
            } else if (attribute.getSizes() == null || attribute.getSizes().length == 0) {
                return "Product's attribute must have sizes";
            } else if (attribute.getQuantity() == null || attribute.getQuantity().length == 0) {
                return "Product's attribute must have quantity for each size";
            } else if (attribute.getImages() == null || attribute.getImages().length < 1 || attribute.getImages().length > 4) {
                return "Product's attribute must have 1 image at least and 4 images at most";
            } else if (attribute.getColor() == null || attribute.getColor().isBlank()) {
                return "Product's attribute must have color";
            }
        }

        return null;
    }


    // check if product adding request is qualified to proceed
    public String messageForUnqualifiedAddingRequest(ProductAddingRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return "Please input product's name";
        } else if (request.getOriginalPrice() < 1 || request.getSellingPrice() < 1) {
            return "Prices must be higher than 0";
        } else if (request.getBrand() == null || request.getBrand().isBlank()) {
            return "Please input product's brand";
        } else if (request.getAttributes() == null || request.getAttributes().isEmpty()) {
            return "Please input product's features or attributes";
        } else if (request.getImportDate() == null) {
            return "Please input product's import date";
        } else return null;
    }
}
