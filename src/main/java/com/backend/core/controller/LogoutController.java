package com.backend.core.controller;

import com.backend.core.entity.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public ApiResponse logout(HttpSession session) {
        try {
            session.removeAttribute("currentUser");
            return new ApiResponse("success", "Logout successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", e.toString());
        }
    }
}
