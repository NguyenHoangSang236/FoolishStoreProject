package com.backend.core.abstractclasses;

import com.backend.core.entity.Account;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.checkerframework.checker.units.qual.A;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AuthenticationController {
    protected AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public abstract ApiResponse loginIntoSystem(@RequestBody Account account, HttpSession session);

    public abstract ApiResponse logoutFromSystem(HttpSession session);

    public abstract ApiResponse registerNewAccount(@Validated @RequestBody Account account, BindingResult bindingResult);
}
