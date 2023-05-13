package com.backend.core.controller;

import com.backend.core.entity.tableentity.Account;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/systemAuthentication", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SystemAuthenticationController extends com.backend.core.abstractclasses.AuthenticationController {
    public SystemAuthenticationController(AuthenticationService authenticationService) {
        super(authenticationService);
    }



    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginIntoSystem (@Validated @RequestBody Account accountFromUI, HttpSession session) throws URISyntaxException {
        return authenticationService.loginIntoSystem(accountFromUI, session);
    }


    @Override
    @GetMapping("/logout")
    public ApiResponse logoutFromSystem(HttpSession session) {
        return authenticationService.logoutFromSystem(session);
    }


    @Override
    @PostMapping("/register")
    public ApiResponse registerNewAccount(@Validated @RequestBody  Account account, BindingResult bindingResult) {
        return authenticationService.registerNewAccount(account, bindingResult);
    }


    @Override
    @PostMapping("/forgotPassword")
    public ResponseEntity<ApiResponse> forgotPassword(@Validated @RequestBody String accJson, BindingResult bindingResult) throws JsonProcessingException, URISyntaxException {
        ObjectMapper objMapper = new ObjectMapper();
        HashMap<String, Object> map = objMapper.readValue(accJson, new TypeReference<HashMap<String, Object>>(){});

        String username = (String) map.get("userName");
        String email = (String) map.get("email");

        return authenticationService.forgotPassword(username, email);
    }
}
