package com.backend.core.infrastructure.business.account.controller;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.business.authentication.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class SystemAuthenticationController {
    final UseCaseExecutor useCaseExecutor;
    final LoginUseCase loginUseCase;
    final LogoutUseCase logoutUseCase;
    final RegisterUseCase registerUseCase;
    final ChangePasswordUseCase changePasswordUseCase;
    final UpdatePersonalProfileUseCase updatePersonalProfileUseCase;
    final ForgotPasswordUseCase forgotPasswordUseCase;


    @PostMapping("/unauthen/systemAuthentication/login")
    public CompletableFuture<ResponseEntity<ApiResponse>> loginIntoSystem(@RequestBody Account accountFromUI) {
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
    public CompletableFuture<ResponseEntity<ApiResponse>> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpServletRequest request) {
        return useCaseExecutor.execute(
                changePasswordUseCase,
                new ChangePasswordUseCase.InputValue(newPassword, oldPassword, request),
                ResponseMapper::map
        );
    }

    @PostMapping("/authen/systemAuthentication/updateProfile")
    public CompletableFuture<ResponseEntity<ApiResponse>> updateProfile(@RequestBody CustomerRenderInfoDTO customerRenderInfoDTO, HttpServletRequest request) {
        return useCaseExecutor.execute(
                updatePersonalProfileUseCase,
                new UpdatePersonalProfileUseCase.InputValue(customerRenderInfoDTO, request),
                ResponseMapper::map
        );
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
    public CompletableFuture<ResponseEntity<ApiResponse>> forgotPassword(@Validated @RequestBody String accJson) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        HashMap<String, Object> map = objMapper.readValue(accJson, new TypeReference<HashMap<String, Object>>() {
        });

        String username = (String) map.get("userName");
        String email = (String) map.get("email");

        return useCaseExecutor.execute(
                forgotPasswordUseCase,
                new ForgotPasswordUseCase.InputValue(username, email),
                ResponseMapper::map
        );
    }
}
