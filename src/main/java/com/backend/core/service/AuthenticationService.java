package com.backend.core.service;

import com.backend.core.entity.tableentity.Account;
import com.backend.core.entity.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface AuthenticationService {
    public ApiResponse loginIntoSystem(Account account, HttpSession session);

    public ApiResponse forgotPassword(Account account);

    public ApiResponse logoutFromSystem(HttpSession session);

    public ApiResponse registerNewAccount(Account account, BindingResult bindingResult);
}
