package com.backend.core.service;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;

@Service
public interface AuthenticationService {
    public ResponseEntity<ApiResponse> loginIntoSystem(Account account, HttpSession session) throws URISyntaxException;

    public ResponseEntity<ApiResponse> forgotPassword(String username, String email) throws URISyntaxException;

    public ApiResponse logoutFromSystem(HttpSession session);

    public ApiResponse registerNewAccount(Account account, BindingResult bindingResult);

    public ApiResponse updateProfile(CustomerRenderInfoDTO customerInfo, HttpSession session);
}
