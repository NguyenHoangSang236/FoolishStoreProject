package com.backend.core.usecase.usecases.google;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.google.repository.LanguagesRepository;
import com.backend.core.usecase.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ViewLanguageListUseCase extends UseCase<ViewLanguageListUseCase.InputValue, ApiResponse> {
    @Autowired
    LanguagesRepository languagesRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        return  new ApiResponse("success", languagesRepo.getAvailableLanguageList(), HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {}
}
