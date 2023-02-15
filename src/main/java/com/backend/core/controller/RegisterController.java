package com.backend.core.controller;

import com.backend.core.entity.Account;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.service.GoogleDriveService;
import com.backend.core.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @Autowired
    GoogleDriveService googleDriveService;

    @PostMapping
    public ApiResponse registerNewUserAccount(@Validated @RequestBody Account account, BindingResult bindingResult) {
        try{
            account.setRole("user");
            String result = registerService.registerNewAccount(account, bindingResult);
            if(result.equals("Register successfully")) {
                return new ApiResponse("success", result);
            }
            else return new ApiResponse("failed", result);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return new ApiResponse("failed", e.toString());
        }
    }


//    @GetMapping
//    public void testUploadFile() throws IOException {
//        System.out.println(GoogleDriveService.uploadBasic("test image", "/home/mr/Downloads/index.jpeg"));
//    }
}
