package com.backend.core.infrastructure.business.account.controller;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutorImpl;
import com.backend.core.usecase.usecases.authentication.LoginUseCase;
import com.backend.core.usecase.usecases.authentication.LogoutUseCase;
import com.backend.core.usecase.usecases.authentication.RegisterUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class SystemAuthenticationController {
    final UseCaseExecutorImpl useCaseExecutor;
    final LoginUseCase loginUseCase;
    final LogoutUseCase logoutUseCase;
    final RegisterUseCase registerUseCase;


    @PostMapping("/unauthen/systemAuthentication/login")
    public CompletableFuture<ResponseEntity<ApiResponse>> loginIntoSystem(@RequestBody Account accountFromUI, HttpServletRequest request) throws URISyntaxException {
        return useCaseExecutor.execute(
                loginUseCase,
                new LoginUseCase.InputValue(accountFromUI),
                ResponseMapper::map
        );
    }


    @GetMapping("/authen/systemAuthentication/logout")
    public CompletableFuture<ResponseEntity<ApiResponse>> logoutFromSystem(HttpServletRequest request) {
        return useCaseExecutor.execute(
                logoutUseCase,
                new LogoutUseCase.InputValue(request),
                ResponseMapper::map
        );
    }

    @GetMapping("/authen/systemAuthentication/changePassword")
    public ResponseEntity changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpServletRequest request) throws URISyntaxException {
        return null;
    }

    @PostMapping("/authen/systemAuthentication/updateProfile")
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody CustomerRenderInfoDTO customerRenderInfoDTO, HttpServletRequest request) {
        return null;
    }


    @PostMapping("/unauthen/systemAuthentication/register")
    public CompletableFuture<ResponseEntity<ApiResponse>> registerNewAccount(@Validated @RequestBody Account account, BindingResult bindingResult) {
        return useCaseExecutor.execute(
                registerUseCase,
                new RegisterUseCase.InputValue(account, bindingResult),
                ResponseMapper::map
        );
    }


    @PostMapping("/unauthen/systemAuthentication/forgotPassword")
    public ResponseEntity<ApiResponse> forgotPassword(@Validated @RequestBody String accJson, BindingResult bindingResult) throws JsonProcessingException, URISyntaxException {
        return null;

    }
}
