package com.backend.core.serviceImpl.admin;

import com.backend.core.entities.embededkey.ProductImagesManagementPrimaryKeys;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.product.ProductAttribute;
import com.backend.core.entities.requestdto.product.ProductDetailsRequestDTO;
import com.backend.core.entities.responsedto.AuthenProductRenderInfoDTO;
import com.backend.core.entities.tableentity.*;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.catalog.CatalogRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.product.*;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

enum RequestPurpose {
    EDIT,
    ADD,
}

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
    ProductImportManagementRepository productImportManagementRepo;

    @Autowired
    ProductRenderInfoRepository productRenderInfoRepo;

    @Autowired
    AuthenProductRenderInfoRepository authenProductRenderInfoRepo;

    @Autowired
    CatalogRepository catalogRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        try {
            ProductDetailsRequestDTO request = (ProductDetailsRequestDTO) paramObj;
            String unqualifiedRequestMsg = messageForUnqualifiedAddingRequest(request, RequestPurpose.ADD);

            if (unqualifiedRequestMsg != null) {
                return new ResponseEntity<>(new ApiResponse("failed", unqualifiedRequestMsg), HttpStatus.BAD_REQUEST);
            } else
                return saveProductProcess(request, RequestPurpose.ADD);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            ProductDetailsRequestDTO request = (ProductDetailsRequestDTO) paramObj;
            String unqualifiedRequestMsg = messageForUnqualifiedAddingRequest(request, RequestPurpose.EDIT);

            if (unqualifiedRequestMsg != null) {
                return new ResponseEntity<>(new ApiResponse("failed", unqualifiedRequestMsg), HttpStatus.BAD_REQUEST);
            } else
                return saveProductProcess(request, RequestPurpose.EDIT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        List<AuthenProductRenderInfoDTO> productDetails = new ArrayList<>();

        try {
            Map<String, Object> requestMap = (Map<String, Object>) paramObj;

            int id = (int) requestMap.get("productId");
            boolean showFull = (boolean) requestMap.get("showFull");

            productDetails = showFull
                    ? authenProductRenderInfoRepo.getAuthenProductFullDetails(id)
                    : authenProductRenderInfoRepo.getAuthenProductDetails(id);

            if (!productDetails.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("success", productDetails), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    // save new product process
    public ResponseEntity<ApiResponse> saveProductProcess(ProductDetailsRequestDTO request, RequestPurpose purpose) {
        // save product first
        Product product = new Product();
        List<Catalog> categoryList = new ArrayList<>();
        Product existedProduct = (purpose.equals(RequestPurpose.ADD))
                ? productRepo.getProductByFullName(request.getName().toLowerCase())
                : productRepo.getProductById(request.getId());

        // if this product is existed and the API is for adding -> error
        if (existedProduct != null && purpose.equals(RequestPurpose.ADD)) {
            return new ResponseEntity<>(new ApiResponse("failed", "This product has been existed"), HttpStatus.BAD_REQUEST);
        }
        // if this product is not existed and the API is for editing -> error
        else if (existedProduct == null && purpose.equals(RequestPurpose.EDIT)) {
            return new ResponseEntity<>(new ApiResponse("failed", "This product does not exist"), HttpStatus.BAD_REQUEST);
        }

        // build Product from ProductAddingRequestDTO
        product.getProductFromProductDetailsRequest(request);
        product.setCatalogs(categoryList);

        // save product management, catalogs_with_products and product images later
        return saveOtherTables(request, product, purpose);
    }


    // save data to other tables process
    public ResponseEntity<ApiResponse> saveOtherTables(ProductDetailsRequestDTO request, Product product, RequestPurpose purpose) {
        List<ProductAttribute> attributes = request.getAttributes();
        String unqualifiedAttrMsg = messageForUnqualifiedAttribute(attributes);

        // check if every attribute is qualified to proceed
        if (unqualifiedAttrMsg != null) {
            return new ResponseEntity<>(new ApiResponse("failed", unqualifiedAttrMsg), HttpStatus.BAD_REQUEST);
        } else {
            productRepo.save(product);

            if(purpose.equals(RequestPurpose.EDIT)) {
                customQueryRepo.deleteCatalogsWithProductsByProductId(product.getId());
            }

            for (Integer cateId : request.getCategoryIds()) {
                Catalog category = catalogRepo.getCatalogById(cateId);

                if(category == null) {
                    return new ResponseEntity<>(new ApiResponse("failed", "Category does not exist"), HttpStatus.BAD_REQUEST);
                }

                customQueryRepo.insertCatalogWithProducts(cateId, product.getId());
            }


            for (ProductAttribute attribute : attributes) {
                if (purpose.equals(RequestPurpose.ADD)) {
                    saveNewProductManagement(attribute, product);
                } else {
                    saveEditedProductManagement(attribute, product);
                }

                saveProductImagesManagement(attribute, product);
            }

            if (purpose.equals(RequestPurpose.ADD)) {
                return new ResponseEntity<>(new ApiResponse("success", "Add new product successfully"), HttpStatus.OK);
            } else return new ResponseEntity<>(new ApiResponse("success", "Edit product successfully"), HttpStatus.OK);
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


    // save new data to product management and product import management tables
    public void saveNewProductManagement(ProductAttribute attribute, Product product) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // save edited data to product management and product import management tables
    public void saveEditedProductManagement(ProductAttribute attribute, Product product) {
        try {
            List<ProductManagement> pmList = productManagementRepo.getProductsManagementListByProductIDAndColor(
                    product.getId(),
                    attribute.getColor()
            );

            // existed product color
            if (pmList != null && !pmList.isEmpty()) {
                List<Integer> tblAvaiQuantityList = pmList.stream().map(pm -> pm.getAvailableQuantity()).collect(Collectors.toList());
                List<Integer> rqQuantityList = new ArrayList<>();

                List<String> tblSizeList = pmList.stream().map(pm -> pm.getSize()).collect(Collectors.toList());
                List<String> rqSizeList = new ArrayList<>();

                Collections.addAll(rqSizeList, attribute.getSizes());

                for (int elem : attribute.getQuantity()) {
                    rqQuantityList.add(elem);
                }

                // there are some changes on products size or quantity -> process changes
                if (!tblSizeList.equals(rqSizeList) || !tblAvaiQuantityList.equals(rqQuantityList)) {
                    int listSize = pmList.size();

                    for (int i = 0; i < listSize; i++) {
                        boolean isDuplicate = false;

                        for (int j = 0; j < rqSizeList.size(); j++) {
                            // request size is duplicate with table size -> update quantity and import date of that size in database
                            if (tblSizeList.get(i).equals(rqSizeList.get(j))) {
                                ProductManagement pm = productManagementRepo.getProductManagementById(
                                        pmList.get(i).getId()
                                );

                                pm.setAvailableQuantity(rqQuantityList.get(j));
                                pm.setProduct(product);

                                productManagementRepo.save(pm);

                                rqSizeList.remove(j);
                                rqQuantityList.remove(j);

                                isDuplicate = true;
                                break;
                            }
                        }

                        // the size in table does not match with any in request's -> set available quantity = 0 - >sold out
                        if (!isDuplicate) {
                            ProductManagement pm = productManagementRepo.getProductManagementById(
                                    pmList.get(i).getId()
                            );

                            pm.setAvailableQuantity(0);
                            pm.setProduct(product);

                            productManagementRepo.save(pm);
                        }
                    }

                    // new data at product management for new size
                    if (!rqSizeList.isEmpty()) {
                        for (int i = 0; i < rqSizeList.size(); i++) {
                            ProductManagement pm = new ProductManagement();

                            pm.setColor(attribute.getColor());
                            pm.setSize(rqSizeList.get(i));
                            pm.setAvailableQuantity(rqQuantityList.get(i));
                            pm.setProduct(product);

                            productManagementRepo.save(pm);

                            pm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                                    product.getId(),
                                    attribute.getColor(),
                                    rqSizeList.get(i)
                            );
                        }
                    }
                }
            }
            // new product color
            else {
                saveNewProductManagement(attribute, product);
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
    public String messageForUnqualifiedAddingRequest(ProductDetailsRequestDTO request, RequestPurpose purpose) {
        if (purpose.equals(RequestPurpose.EDIT) && request.getId() <= 0) {
            return "Please input product's id";
        } else if (request.getName() == null || request.getName().isBlank()) {
            return "Please input product's name";
        } else if (request.getOriginalPrice() < 1 || request.getSellingPrice() < 1) {
            return "Prices must be higher than 0";
        } else if (request.getBrand() == null || request.getBrand().isBlank()) {
            return "Please input product's brand";
        } else if (request.getAttributes() == null || request.getAttributes().isEmpty()) {
            return "Please input product's features or attributes";
        } else return null;
    }
}