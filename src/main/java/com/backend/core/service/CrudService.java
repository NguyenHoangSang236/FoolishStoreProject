package com.backend.core.service;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.enums.RenderTypeEnum;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CrudService {
    // generate post response for creation
    ApiResponse creationalResponse(Object paramObj, HttpSession session);

    // generate post response for removal
    ApiResponse removingResponse(Object paramObj, HttpSession session);

    // generate post response for update
    ApiResponse updatingResponse(List<Object> paramObjList, HttpSession session);

    // generate post response for reading a single object by posting method
    ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session);

    // generate post response for reading a list of objects by posting method
    ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session);

    // generate post response for reading a list of objects by posting method
    ApiResponse readingResponse(HttpSession session, RenderTypeEnum renderType);

    // generate post response for reading using object's id
    ApiResponse readingById(int id, HttpSession session);
}
