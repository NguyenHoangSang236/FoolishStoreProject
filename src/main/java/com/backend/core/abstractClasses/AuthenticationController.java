package com.backend.core.abstractClasses;

import com.backend.core.entities.responsedto.CustomerRenderInfoDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;

public abstract class AuthenticationController {
    protected AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public abstract ResponseEntity loginIntoSystem(@RequestBody Account account, HttpServletRequest request) throws URISyntaxException;

    public abstract ResponseEntity logoutFromSystem(HttpServletRequest request);

    public abstract ResponseEntity changePassword(String oldPassword, String newPassword, HttpServletRequest request) throws URISyntaxException;

    public abstract ResponseEntity updateProfile(@RequestBody CustomerRenderInfoDTO customerRenderInfoDTO, HttpServletRequest request);

    public abstract ResponseEntity registerNewAccount(@Validated @RequestBody Account account, BindingResult bindingResult);

    public abstract ResponseEntity forgotPassword(@Validated @RequestBody String accountInfoJson, BindingResult bindingResult) throws JsonProcessingException, URISyntaxException;
}
