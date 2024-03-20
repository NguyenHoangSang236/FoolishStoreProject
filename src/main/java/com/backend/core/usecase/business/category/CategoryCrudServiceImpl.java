package com.backend.core.usecase.business.category;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.business.category.repository.CatalogRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Qualifier("CategoryCrudServiceImpl")
@RequiredArgsConstructor
public class CategoryCrudServiceImpl implements CrudService {
    @Autowired
    CatalogRepository categoryRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        try {
            Catalog newCatalog = (Catalog) paramObj;

            if (newCatalog.getName() == null || newCatalog.getName().isBlank() || newCatalog.getImage() == null || newCatalog.getImage().isBlank()) {
                return new ResponseEntity<>(new ApiResponse("failed", "New category must have an image and name"), HttpStatus.BAD_REQUEST);
            }

            String firstLetterCapitalCatalogName = valueRenderUtils.capitalizeFirstLetterOfEachWord(newCatalog.getName().toLowerCase());
            Catalog existedCatalog = categoryRepo.getCatalogByName(firstLetterCapitalCatalogName);

            if (existedCatalog != null) {
                return new ResponseEntity<>(new ApiResponse("failed", "This category has already existed"), HttpStatus.BAD_REQUEST);
            }

            newCatalog.setName(firstLetterCapitalCatalogName);
            categoryRepo.save(newCatalog);

            return new ResponseEntity<>(new ApiResponse("success", "Add new category successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        try {
            if (id < 1) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            Catalog selectedCatalog = categoryRepo.getCatalogById(id);

            if (selectedCatalog == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            customQueryRepo.deleteCatalogsWithProductsById(id);
            categoryRepo.delete(selectedCatalog);

            return new ResponseEntity<>(new ApiResponse("success", "Remove category successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            Catalog updatingCatalog = (Catalog) paramObj;

            if (updatingCatalog.getName() == null ||
                    updatingCatalog.getName().isBlank() ||
                    updatingCatalog.getImage() == null ||
                    updatingCatalog.getImage().isBlank() ||
                    updatingCatalog.getId() == 0) {
                return new ResponseEntity<>(new ApiResponse("failed", "Updating category must have id and an image or name"), HttpStatus.BAD_REQUEST);
            }

            Catalog existedCatalog = categoryRepo.getCatalogById(updatingCatalog.getId());

            if (existedCatalog == null) {
                return new ResponseEntity<>(new ApiResponse("failed", "This category does not exist"), HttpStatus.BAD_REQUEST);
            }

            String firstWordCapitalCatalogName = valueRenderUtils.capitalizeFirstLetterOfEachWord(updatingCatalog.getName());
            Set<String> catalogNames = categoryRepo.getAllCatalogName();

            if (catalogNames.contains(firstWordCapitalCatalogName)) {
                return new ResponseEntity<>(new ApiResponse("failed", "This category name has already existed"), HttpStatus.BAD_REQUEST);
            }

            updatingCatalog.setName(firstWordCapitalCatalogName);
            categoryRepo.save(updatingCatalog);

            return new ResponseEntity<>(new ApiResponse("success", "Update category successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // get all categories
    @Override
    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        try {
            List<Catalog> categoryList = categoryRepo.getAllCatalogs();
            return new ResponseEntity<>(new ApiResponse("success", categoryList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
