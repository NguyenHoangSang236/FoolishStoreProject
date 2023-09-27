package com.backend.core.service;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.responsedto.CustomerRenderInfoDTO;
import com.backend.core.entities.tableentity.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;

@Service
public interface AuthenticationService {
    ResponseEntity<ApiResponse> loginIntoSystem(Account account, HttpServletRequest request) throws URISyntaxException;

    ResponseEntity<ApiResponse> forgotPassword(String username, String email) throws URISyntaxException;

    ResponseEntity<ApiResponse> logoutFromSystem(HttpServletRequest request);

    ResponseEntity<ApiResponse> registerNewAccount(Account account, BindingResult bindingResult);

    ResponseEntity<ApiResponse> updateProfile(CustomerRenderInfoDTO customerInfo, HttpServletRequest request);
}
