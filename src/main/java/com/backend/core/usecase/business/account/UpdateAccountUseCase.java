package com.backend.core.usecase.business.account;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.AccountStatusEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

@Component
public class UpdateAccountUseCase extends UseCase<UpdateAccountUseCase.InputValue, ApiResponse> {
    @Autowired
    AccountRepository accountRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        // convert request param into Account
        Account account = input.getAccount();

        int id = account.getId();
        String status = account.getStatus();

        // get Account by id
        Account selectedAcc = accountRepo.getAccountByID(id);

        // check if selected account is existed
        if (selectedAcc == null) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        }

        // check the status is correct or not
        if (EnumUtils.findEnumInsensitiveCase(AccountStatusEnum.class, status) == null) {
            return new ApiResponse("failed", ErrorTypeEnum.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }

        // set status to CUSTOMER account and the status of it is DIFFERENT from request param's one
        if (!selectedAcc.getStatus().equals(account.getStatus()) && !selectedAcc.getRole().equals(RoleEnum.ADMIN.name())) {
            accountRepo.updateAccountStatus(status, account.getId());

            return new ApiResponse("success", "Update account successfully", HttpStatus.OK);
        } else {
            return new ApiResponse("failed", ErrorTypeEnum.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        Account account;
    }
}
