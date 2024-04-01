package com.backend.core.usecase.business.firebase;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.delivery.model.DeviceFcmToken;
import com.backend.core.infrastructure.business.firebase.repository.DeviceFcmTokenRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AddNewFcmTokenUseCase extends UseCase<AddNewFcmTokenUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    DeviceFcmTokenRepository deviceFcmTokenRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        Account currentAccount = valueRenderUtils.getCurrentAccountFromRequest(input.getHttpRequest());

        if (currentAccount == null) {
            return new ApiResponse("failed", ErrorTypeEnum.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        } else {
            DeviceFcmToken newToken = new DeviceFcmToken();

            newToken.setAccount(currentAccount);
            newToken.setPhoneFcmToken(input.getFcmToken());

            deviceFcmTokenRepo.save(newToken);

            return new ApiResponse("success", "Add new phone fcm token successfully", HttpStatus.OK);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        String fcmToken;
        HttpServletRequest httpRequest;
    }
}
