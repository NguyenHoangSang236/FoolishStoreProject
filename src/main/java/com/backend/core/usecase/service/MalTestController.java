package com.backend.core.usecase.service;

import com.backend.core.entity.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MalTestController {
    @Autowired
    MailService mailService;

    @GetMapping("unauthen/sendMail")
    public ApiResponse sendMail() {
        mailService.sendMail();

        return new ApiResponse("success", "Send mail success", HttpStatus.OK);
    }
}
