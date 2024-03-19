package com.backend.core.usecase.usecases.authentication;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.statics.StringTypeEnum;
import com.backend.core.usecase.util.handler.BindExceptionHandler;
import com.backend.core.usecase.util.process.CheckUtils;
import com.backend.core.usecase.util.process.JwtUtils;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Component
public class RegisterUseCase extends UseCase<RegisterUseCase.InputValue, ApiResponse> {
    @Autowired
    CheckUtils checkUtils;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        Account accountFromUI = input.getAccount();
        BindingResult bindingResult = input.getBindingResult();

        accountFromUI.setRole(RoleEnum.CUSTOMER.name());
        Customer customer = accountFromUI.getCustomer();
        customer.setImage("1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R");

        try {
            //check for null information
            if (bindingResult.hasErrors()) {
                return new ApiResponse("failed", BindExceptionHandler.getHandleBindException(new BindException(bindingResult)), HttpStatus.BAD_REQUEST);
            }
            //check for null password
            else if (accountFromUI.getPassword().isEmpty() || accountFromUI.getPassword().isBlank()) {
                return new ApiResponse("failed", "Password can not be null !!", HttpStatus.BAD_REQUEST);
            }
            // check password format
            if (accountFromUI.getPassword().length() < 6) {
                return new ApiResponse("failed", "Password must contain more than 6 letters", HttpStatus.BAD_REQUEST);
            }
            //check valid username and password
            else if (checkUtils.checkValidStringType(accountFromUI.getUsername(), StringTypeEnum.HAS_NO_SPACE) ||
                    checkUtils.checkValidStringType(accountFromUI.getPassword(), StringTypeEnum.HAS_NO_SPACE)) {
                return new ApiResponse("failed", "Please remove all spaces in Username and Password !!", HttpStatus.BAD_REQUEST);
            }
            //check for existed username
            else if (accountRepo.getAccountByUserName(accountFromUI.getUsername()) != null) {
                return new ApiResponse("failed", "This username has already been existed !!", HttpStatus.BAD_REQUEST);
            }
//            //check valid phone number
//            else if (!checkUtils.isValidPhoneNumber(customer.getPhoneNumber())) {
//                return "This is not a phone number !!";
//            }
            //check valid VN phone number
            else if (customer.getPhoneNumber().length() != 10) {
                return new ApiResponse("failed", "Phone number must be 10-digit !!", HttpStatus.BAD_REQUEST);
            }
            //check valid email
            else if (!checkUtils.isValidEmail(customer.getEmail())) {
                return new ApiResponse("failed", "This is not an email, please input again !!", HttpStatus.BAD_REQUEST);
            }
            //check reused email
            else if (customerRepo.getCustomerByEmail(customer.getEmail()) != null) {
                return new ApiResponse("failed", "This email has been used !!", HttpStatus.BAD_REQUEST);
            }
            //check valid customer full name
            else if (checkUtils.hasSpecialSign(customer.getName())) {
                return new ApiResponse("failed", "Full name can not have special signs !!", HttpStatus.BAD_REQUEST);
            } else {
                String jwt = jwtUtils.generateJwt(accountFromUI);
                String newEncodedPassword = passwordEncoder.encode(accountFromUI.getPassword());

                accountFromUI.setPassword(newEncodedPassword);
                accountFromUI.setCustomer(null);
                accountRepo.save(accountFromUI);

                customer.setAccount(accountFromUI);
                String formattedName = valueRenderUtils.capitalizeFirstLetterOfEachWord(customer.getName());
                customer.setName(formattedName);
                customerRepo.save(customer);

                return new ApiResponse("success", "Register successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        Account account;
        BindingResult bindingResult;
    }
}
