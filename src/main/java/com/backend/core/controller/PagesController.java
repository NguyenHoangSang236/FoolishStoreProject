package com.backend.core.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PagesController {
    @RequestMapping("/index")
    public String getIndex(){
        return "src/main/resources/page/index.html";
    }
}
