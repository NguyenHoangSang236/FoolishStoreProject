package com.backend.core.usecase.usecases.authentication;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LogoutUseCase extends UseCase<LogoutUseCase.InputValue, ApiResponse> {
    @Autowired
    JwtUtils jwtUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        try {
            String jwt = jwtUtils.getJwtFromRequest(input.getRequest());
            jwtUtils.expireJwt(jwt);

            return new ApiResponse("success", "Logout successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements UseCase.InputValues {
        final HttpServletRequest request;
    }
}
