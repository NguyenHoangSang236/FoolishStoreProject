package com.backend.core.service;

import com.backend.core.entity.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrudService {
    ApiResponse createResponse(Object obj, HttpSession session);
    ApiResponse removeResponse(Object obj, HttpSession session);
    ApiResponse updateResponse(Object obj, HttpSession session);
    ApiResponse readSingleResponse(Object obj, HttpSession session);
    ApiResponse readListReponse(List<Object> objList, HttpSession session);
}
