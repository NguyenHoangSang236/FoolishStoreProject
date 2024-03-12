package com.backend.core.infrastructure.business.google.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.google.gateway.GoogleTranslateDTO;
import com.backend.core.infrastructure.business.google.repository.LanguagesRepository;
import com.backend.core.usecase.service.GoogleTranslateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/unauthen/translator", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
// @CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class TranslatorController {
    @Autowired
    LanguagesRepository languagesRepo;


    @PostMapping("/translate")
    public ApiResponse getListOfItems(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleTranslateDTO ggTrans = objectMapper.readValue(json, GoogleTranslateDTO.class);

        return new ApiResponse("success", GoogleTranslateService.translate(ggTrans.getSourceLanguageCode(), "en", ggTrans.getText()));
    }

    @GetMapping("/getAllLanguageList")
    public ApiResponse getAllLanguageList() throws IOException {
        return new ApiResponse("success", languagesRepo.getAccountByUserNameAndPassword());
    }
}
