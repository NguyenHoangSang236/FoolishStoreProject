package com.backend.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PagesController {
    @GetMapping("/")
    public String indexPage() {
        return "";
    }
}
