package com.backend.core.usecase.service;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;

@Service
public interface AuthenticationService {
    ResponseEntity<ApiResponse> loginIntoSystem(Account account, HttpServletRequest request) throws URISyntaxException;

    ResponseEntity<ApiResponse> forgotPassword(String username, String email) throws URISyntaxException;

    ResponseEntity<ApiResponse> changePassword(String oldPassword, String newPassword, HttpServletRequest request) throws URISyntaxException;

    ResponseEntity<ApiResponse> logoutFromSystem(HttpServletRequest request);

    ResponseEntity<ApiResponse> registerNewAccount(Account account, BindingResult bindingResult);

    ResponseEntity<ApiResponse> updateProfile(CustomerRenderInfoDTO customerInfo, HttpServletRequest request);
}
