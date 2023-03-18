package com.backend.core.controller;

import com.backend.core.entity.tableentity.Account;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/systemAuthentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SystemAuthenticationController extends com.backend.core.abstractclasses.AuthenticationController {
    public SystemAuthenticationController(AuthenticationService authenticationService) {
        super(authenticationService);
    }



    @PostMapping("/login")
    @Override
    public ApiResponse loginIntoSystem (@Validated @RequestBody Account accountFromUI, HttpSession session) {
        return authenticationService.loginIntoSystem(accountFromUI, session);
    }


    @GetMapping("/logout")
    @Override
    public ApiResponse logoutFromSystem(HttpSession session) {
        return authenticationService.logoutFromSystem(session);
    }


    @PostMapping("/register")
    @Override
    public ApiResponse registerNewAccount(@Validated @RequestBody  Account account, BindingResult bindingResult) {
        return authenticationService.registerNewAccount(account, bindingResult);
    }


    @PostMapping("/forgotPassword")
    public void forgotPassword() {

    }
}
