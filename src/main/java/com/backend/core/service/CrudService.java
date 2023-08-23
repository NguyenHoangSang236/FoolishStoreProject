package com.backend.core.service;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrudService {
    // generate post response for creation
    ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest);

    // generate post response for creation with a List
    ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest);

    // generate post response for removal
    ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest);

    // generate post response for removal by id
    ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest);

    // generate post response for update
    ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest);

    // generate post response for updating by id
    ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest);

    // generate post response for updating by request
    ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest);

    // generate post response for reading a single object by post method
    ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest);

    // generate post response for reading by a list request by post method
    ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest);

    // generate post response for reading a list of objects by get method
    ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest);

    // generate post response for reading using object's id
    ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest);
}
