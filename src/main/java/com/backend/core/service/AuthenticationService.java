package com.backend.core.service;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entities.tableentity.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;

@Service
public interface AuthenticationService {
    public ResponseEntity loginIntoSystem(Account account, HttpServletRequest request) throws URISyntaxException;

    public ResponseEntity forgotPassword(String username, String email) throws URISyntaxException;

    public ResponseEntity logoutFromSystem(HttpServletRequest request);

    public ResponseEntity registerNewAccount(Account account, BindingResult bindingResult);

    public ResponseEntity updateProfile(CustomerRenderInfoDTO customerInfo, HttpServletRequest request);
}
