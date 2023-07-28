package com.backend.core.service;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.ListRequestDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface CrudService {
    // generate post response for creation
    ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest);

    // generate post response for creation with a List
    ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest);

    // generate post response for removal
    ApiResponse removingResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest);

    // generate post response for update
    ApiResponse updatingResponse(int id, ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest);

    // generate post response for reading a single object by post method
    ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest);

    // generate post response for reading by a list request by post method
    ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest);

    // generate post response for reading a list of objects by get method
    ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest);

    // generate post response for reading using object's id
    ApiResponse readingById(int id, HttpSession session, HttpServletRequest httpRequest);
}
