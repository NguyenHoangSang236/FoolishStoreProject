package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.tableentity.Catalog;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.CatalogRepository;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("CategoryCrudServiceImpl")
public class CategoryCrudServiceImpl implements CrudService {
    @Autowired
    CatalogRepository categoryRepo;


    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session) {
        return null;
    }

    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session) {
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
        return null;
    }

    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session) {
        return null;
    }

    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType) {
        try {
            List<Catalog> categoryList = categoryRepo.getAllCatalogs();
            return new ApiResponse("success", categoryList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("success", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }

    @Override
    public ApiResponse readingById(int id, HttpSession session) {
        return null;
    }
}
