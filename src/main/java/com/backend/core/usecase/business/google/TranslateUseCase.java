package com.backend.core.usecase.business.google;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.google.gateway.GoogleTranslateDTO;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.GoogleTranslateService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TranslateUseCase extends UseCase<TranslateUseCase.InputValue, ApiResponse> {
    @Override
    public ApiResponse execute(InputValue input) {
        try {
            GoogleTranslateDTO ggTrans = input.getGoogleTranslateRequest();
            return new ApiResponse("success", GoogleTranslateService.translate(ggTrans.getSourceLanguageCode(), "en", ggTrans.getText()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        GoogleTranslateDTO googleTranslateRequest;
    }
}
