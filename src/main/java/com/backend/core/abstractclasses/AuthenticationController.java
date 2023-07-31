package com.backend.core.abstractclasses;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.dto.ApiResponse;
import com.backend.core.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
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

    public abstract ResponseEntity<ApiResponse> loginIntoSystem(@RequestBody Account account, HttpSession session) throws URISyntaxException;

    public abstract ApiResponse logoutFromSystem(HttpSession session);

    public abstract ApiResponse updateProfile(@RequestBody CustomerRenderInfoDTO customerRenderInfoDTO, HttpSession session);

    public abstract ApiResponse registerNewAccount(@Validated @RequestBody Account account, BindingResult bindingResult);

    public abstract ResponseEntity<ApiResponse> forgotPassword(@Validated @RequestBody String accountInfoJson, BindingResult bindingResult) throws JsonProcessingException, URISyntaxException;
}
