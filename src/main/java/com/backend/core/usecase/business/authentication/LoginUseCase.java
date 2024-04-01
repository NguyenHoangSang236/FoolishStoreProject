package com.backend.core.usecase.business.authentication;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.business.account.dto.StaffRenderInfoDTO;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.infrastructure.business.account.repository.CustomerRenderInfoRepository;
import com.backend.core.infrastructure.business.account.repository.StaffRenderInfoRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.JwtService;
import com.backend.core.usecase.statics.AccountStatusEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase extends UseCase<LoginUseCase.InputValue, ApiResponse> {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    StaffRenderInfoRepository staffRenderInfoRepo;
    @Autowired
    CustomerRenderInfoRepository customerRenderInfoRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        try {
            Account loginAcc = new Account();
            Account account = input.getAccount();
            String jwt;
            StaffRenderInfoDTO staffInfo = new StaffRenderInfoDTO();
            CustomerRenderInfoDTO customerInfo = new CustomerRenderInfoDTO();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getUsername(),
                            account.getPassword()
                    )
            );

            loginAcc = accountRepo.getAccountByUserName(account.getUsername());

            // check the status of the account is ALLOWED or BANNED
            if (loginAcc != null && loginAcc.getStatus().equals(AccountStatusEnum.ALLOWED.name())) {
                jwt = jwtService.generateJwt(loginAcc);

                loginAcc.setCurrentJwt(jwt);
                accountRepo.save(loginAcc);

                // when the account is Admin
                if (loginAcc.getRole().equals(RoleEnum.ADMIN.name())) {
                    staffInfo = staffRenderInfoRepo.getStaffInfoByUserName(account.getUsername());

                    return new ApiResponse("success", staffInfo, jwt, HttpStatus.OK);
                }
                // when the account is Customer
                else if (loginAcc.getRole().equals(RoleEnum.CUSTOMER.name())) {
                    customerInfo = customerRenderInfoRepo.getCustomerInfoByUserName(account.getUsername());

                    return new ApiResponse("success", customerInfo, jwt, HttpStatus.OK);
                } else
                    return new ApiResponse("failed", "This role is not existed", HttpStatus.BAD_REQUEST);
            } else
                return new ApiResponse("failed", "Incorrect password or user name, please check again!", HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        Account account;
    }
}
