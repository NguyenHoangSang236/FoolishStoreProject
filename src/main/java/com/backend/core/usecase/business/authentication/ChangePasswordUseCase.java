package com.backend.core.usecase.business.authentication;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class ChangePasswordUseCase extends UseCase<ChangePasswordUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AccountRepository accountRepo;

    @Override
    public ApiResponse execute(InputValue input) {
        String newPassword = input.getNewPassword();
        String oldPassword = input.getOldPassword();
        HttpServletRequest request = input.getRequest();

        try {
            // check password format
            if (newPassword.length() < 6 || newPassword.trim().contains(" ")) {
                return new ApiResponse("failed", "Password must contain more than 6 letters and must not have any space", HttpStatus.BAD_REQUEST);
            }

            Account account = valueRenderUtils.getCurrentAccountFromRequest(request);
            String newEncodedPassword = passwordEncoder.encode(newPassword);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getUsername(),
                            oldPassword
                    )
            );

            account.setPassword(newEncodedPassword);
            accountRepo.save(account);

            return new ApiResponse("success", "Changed password successfully", HttpStatus.OK);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ApiResponse("failed", "Incorrect old password", HttpStatus.BAD_REQUEST);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        String newPassword;
        String oldPassword;
        HttpServletRequest request;
    }
}
