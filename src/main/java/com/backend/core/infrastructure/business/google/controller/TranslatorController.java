package com.backend.core.infrastructure.business.google.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.google.gateway.GoogleTranslateDTO;
import com.backend.core.infrastructure.business.google.repository.LanguagesRepository;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.service.GoogleTranslateService;
import com.backend.core.usecase.usecases.google.TranslateUseCase;
import com.backend.core.usecase.usecases.google.ViewLanguageListUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/unauthen/translator", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class TranslatorController {;
    final UseCaseExecutor useCaseExecutor;
    final TranslateUseCase translateUseCase;
    final ViewLanguageListUseCase viewLanguageListUseCase;


    @PostMapping("/translate")
    public CompletableFuture<ResponseEntity<ApiResponse>> translateToEnglish(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleTranslateDTO ggTrans = objectMapper.readValue(json, GoogleTranslateDTO.class);

        return useCaseExecutor.execute(
                translateUseCase,
                new TranslateUseCase.InputValue(ggTrans),
                ResponseMapper::map
        );
    }

    @GetMapping("/getAllLanguageList")
    public CompletableFuture<ResponseEntity<ApiResponse>> getAvailableLanguageList() throws IOException {
        return useCaseExecutor.execute(
                viewLanguageListUseCase,
                new ViewLanguageListUseCase.InputValue(),
                ResponseMapper::map
        );
    }
}
