package com.backend.core.usecase.service;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudService {
    // generate post response for creation
    default ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for creation with a List
    default ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for removal
    default ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for removal by id
    default ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for update
    default ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for updating by id
    default ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for updating by request
    default ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for reading a single object by post method
    default ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for reading by a list request by post method
    default ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for reading a list of objects by get method
    default ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }

    // generate post response for reading using object's id
    default ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }
}
