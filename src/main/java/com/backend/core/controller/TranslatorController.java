package com.backend.core.controller;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.GoogleTranslateDTO;
import com.backend.core.translator.GoogleTranslateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/translator", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TranslatorController {
    @PostMapping("/translate")
    public ApiResponse getListOfItems(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleTranslateDTO ggTrans = objectMapper.readValue(json, GoogleTranslateDTO.class);

        return new ApiResponse("success", GoogleTranslateService.translate(ggTrans.getSourceLanguageCode(), "en", ggTrans.getText()));
    }
}