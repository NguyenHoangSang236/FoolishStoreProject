package com.backend.core.serviceimpl;

import com.backend.core.entities.dto.ApiResponse;
import com.backend.core.entities.dto.ListRequestDTO;
import com.backend.core.entities.tableentity.Catalog;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.CatalogRepository;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Qualifier("CategoryCrudServiceImpl")
public class CategoryCrudServiceImpl implements CrudService {
    @Autowired
    CatalogRepository categoryRepo;


    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse removingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse removingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseByList(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    // get all categories
    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest) {
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
    public ApiResponse readingById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }
}
