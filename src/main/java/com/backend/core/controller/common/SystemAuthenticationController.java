package com.backend.core.controller.common;

import com.backend.core.abstractClasses.AuthenticationController;
import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SystemAuthenticationController extends AuthenticationController {
    public SystemAuthenticationController(AuthenticationService authenticationService) {
        super(authenticationService);
    }


    @Override
    @PostMapping("/unauthen/systemAuthentication/login")
    public ResponseEntity loginIntoSystem(@RequestBody Account accountFromUI, HttpServletRequest request) throws URISyntaxException {
        return authenticationService.loginIntoSystem(accountFromUI, request);
    }


    @Override
    @GetMapping("/authen/systemAuthentication/logout")
    public ResponseEntity logoutFromSystem(HttpServletRequest request) {
        return authenticationService.logoutFromSystem(request);
    }

    @Override
    @PostMapping("/unauthen/systemAuthentication/updateProfile")
    public ResponseEntity updateProfile(@RequestBody CustomerRenderInfoDTO customerRenderInfoDTO, HttpServletRequest request) {
        return authenticationService.updateProfile(customerRenderInfoDTO, request);
    }


    @Override
    @PostMapping("/unauthen/systemAuthentication/register")
    public ResponseEntity registerNewAccount(@Validated @RequestBody Account account, BindingResult bindingResult) {
        return authenticationService.registerNewAccount(account, bindingResult);
    }


    @Override
    @PostMapping("/unauthen/systemAuthentication/forgotPassword")
    public ResponseEntity forgotPassword(@Validated @RequestBody String accJson, BindingResult bindingResult) throws JsonProcessingException, URISyntaxException {
        ObjectMapper objMapper = new ObjectMapper();
        HashMap<String, Object> map = objMapper.readValue(accJson, new TypeReference<HashMap<String, Object>>() {
        });

        String username = (String) map.get("userName");
        String email = (String) map.get("email");

        return authenticationService.forgotPassword(username, email);
    }
}
