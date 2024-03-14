package com.backend.core.usecase.business.authentication;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.business.account.dto.StaffRenderInfoDTO;
import com.backend.core.infrastructure.business.account.repository.*;
import com.backend.core.usecase.service.AuthenticationService;
import com.backend.core.usecase.service.GoogleDriveService;
import com.backend.core.usecase.statics.AccountStatusEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.statics.StringTypeEnum;
import com.backend.core.usecase.util.handler.BindExceptionHandler;
import com.backend.core.usecase.util.process.CheckUtils;
import com.backend.core.usecase.util.process.JwtUtils;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    final JwtUtils jwtUtils;
    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    @Autowired
    StaffRepository staffRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    StaffRenderInfoRepository staffRenderInfoRepo;
    @Autowired
    CustomerRenderInfoRepository customerRenderInfoRepo;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    GoogleDriveService googleDriveService;
    @Autowired
    CheckUtils checkUtils;
    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Override
    public ResponseEntity<ApiResponse> loginIntoSystem(Account account, HttpServletRequest request) throws URISyntaxException {
        Account loginAcc = new Account();
        String jwt;
        StaffRenderInfoDTO staffInfo = new StaffRenderInfoDTO();
        CustomerRenderInfoDTO customerInfo = new CustomerRenderInfoDTO();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getUsername(),
                            account.getPassword()
                    )
            );

            loginAcc = accountRepo.getAccountByUserName(account.getUsername());

            // check the status of the account is ALLOWED or BANNED
            if (loginAcc != null && loginAcc.getStatus().equals(AccountStatusEnum.ALLOWED.name())) {
                jwt = jwtUtils.generateJwt(loginAcc);
                loginAcc.setCurrentJwt(jwt);
                accountRepo.save(loginAcc);

                // when the account is Admin
                if (loginAcc.getRole().equals(RoleEnum.ADMIN.name())) {
                    staffInfo = staffRenderInfoRepo.getStaffInfoByUserName(account.getUsername());

                    return new ResponseEntity<>(new ApiResponse("success", staffInfo, jwt), HttpStatus.OK);
                }
                // when the account is Customer
                else if (loginAcc.getRole().equals(RoleEnum.CUSTOMER.name())) {
                    customerInfo = customerRenderInfoRepo.getCustomerInfoByUserName(account.getUsername());

                    return new ResponseEntity<>(new ApiResponse("success", customerInfo, jwt), HttpStatus.OK);
                } else
                    return new ResponseEntity<>(new ApiResponse("failed", "This role is not existed"), HttpStatus.BAD_REQUEST);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", "Incorrect password or user name, please check again!"), HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> forgotPassword(String username, String email) throws URISyntaxException {
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Account currentAccount = accountRepo.getAccountByUserName(username);

            if (currentAccount != null) {
                if (currentAccount.getStatus().equals(AccountStatusEnum.BANNED.name())) {
                    return new ResponseEntity<>(new ApiResponse("failed", "This account has been banned"), responseHeaders, HttpStatus.BAD_REQUEST);
                } else if (currentAccount.getCustomer().getEmail().equals(email)) {
                    String newPassword = getNewTemporaryRandomPasswordMail(username, email);

                    String newEncodedPassword = passwordEncoder.encode(newPassword);
                    currentAccount.setPassword(newEncodedPassword);
                    accountRepo.save(currentAccount);

                    return new ResponseEntity<>(new ApiResponse("success", "Change password successfully"), responseHeaders, HttpStatus.OK);
                } else
                    return new ResponseEntity<>(new ApiResponse("failed", "This email does not belong to this account"), responseHeaders, HttpStatus.BAD_REQUEST);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", "This account is not existed"), responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> changePassword(String oldPassword, String newPassword, HttpServletRequest request) throws URISyntaxException {
        try {
            // check password format
            if(newPassword.length() < 6 || newPassword.trim().contains(" ")) {
                return new ResponseEntity<>(new ApiResponse("failed", "Password must contain more than 6 letters and must not have any space"), HttpStatus.BAD_REQUEST);
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

            return new ResponseEntity<>(new ApiResponse("success", "Changed password successfully"), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();

            if(e instanceof BadCredentialsException) {
                return new ResponseEntity<>(new ApiResponse("failed", "Incorrect old password"), HttpStatus.BAD_REQUEST);
            }
            else
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> logoutFromSystem(HttpServletRequest request) {
        try {
            String jwt = jwtUtils.getJwtFromRequest(request);
            jwtUtils.expireJwt(jwt);

            return new ResponseEntity<>(new ApiResponse("success", "Logout successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> registerNewAccount(Account accountFromUI, BindingResult bindingResult) {
        accountFromUI.setRole(RoleEnum.CUSTOMER.name());
        Customer customer = accountFromUI.getCustomer();
        customer.setImage("1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R");

        try {
            //check for null information
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(new ApiResponse("failed", BindExceptionHandler.getHandleBindException(new BindException(bindingResult))), HttpStatus.BAD_REQUEST);
            }
            //check for null password
            else if (accountFromUI.getPassword().isEmpty() || accountFromUI.getPassword().isBlank()) {
                return new ResponseEntity<>(new ApiResponse("failed", "Password can not be null !!"), HttpStatus.BAD_REQUEST);
            }
            // check password format
            if(accountFromUI.getPassword().length() < 6) {
                return new ResponseEntity<>(new ApiResponse("failed", "Password must contain more than 6 letters"), HttpStatus.BAD_REQUEST);
            }
            //check valid username and password
            else if (checkUtils.checkValidStringType(accountFromUI.getUsername(), StringTypeEnum.HAS_NO_SPACE) ||
                    checkUtils.checkValidStringType(accountFromUI.getPassword(), StringTypeEnum.HAS_NO_SPACE)) {
                return new ResponseEntity<>(new ApiResponse("failed", "Please remove all spaces in Username and Password !!"), HttpStatus.BAD_REQUEST);
            }
            //check for existed username
            else if (accountRepo.getAccountByUserName(accountFromUI.getUsername()) != null) {
                return new ResponseEntity<>(new ApiResponse("failed", "This username has already been existed"), HttpStatus.BAD_REQUEST);
            }
//            //check valid phone number
//            else if (!checkUtils.isValidPhoneNumber(customer.getPhoneNumber())) {
//                return "This is not a phone number !!";
//            }
            //check valid VN phone number
            else if (customer.getPhoneNumber().length() != 10) {
                return new ResponseEntity<>(new ApiResponse("failed", "Phone number must be 10-digit !!"), HttpStatus.BAD_REQUEST);
            }
            //check valid email
            else if (!checkUtils.isValidEmail(customer.getEmail())) {
                return new ResponseEntity<>(new ApiResponse("failed", "This is not an email, please input again !!"), HttpStatus.BAD_REQUEST);
            }
            //check reused email
            else if (customerRepo.getCustomerByEmail(customer.getEmail()) != null) {
                return new ResponseEntity<>(new ApiResponse("failed", "This email has been used !!"), HttpStatus.BAD_REQUEST);
            }
            //check valid customer full name
            else if (checkUtils.hasSpecialSign(customer.getName())) {
                return new ResponseEntity<>(new ApiResponse("failed", "Full name can not have special signs !!"), HttpStatus.BAD_REQUEST);
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

                return new ResponseEntity<>(new ApiResponse("success", "Register successfully"), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateProfile(CustomerRenderInfoDTO customerInfo, HttpServletRequest request) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(request);

        try {
            Customer customer = customerRepo.getCustomerById(customerId);
            customer.setCustomerInfoFromRenderInfo(customerInfo);

            customerRepo.save(customer);

            return new ResponseEntity<>(new ApiResponse("success", "Update profile successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public String getNewTemporaryRandomPasswordMail(String userName, String email) {
        String fromAddress = "foolishstore2@gmail.com";
        String senderName = "Fool!sh Fashion Store";
        String subject = "Your new temporary password";
        String newTempPassoword = valueRenderUtils.randomTemporaryPassword(userName);
        String content = "Dear [[name]],<br>"
                + "Your new temporary password is " + newTempPassoword + "<br>"
                + "Please remember to change a new password for your new account because this temporary password will be changed after you close the website !!<br><br>"
                + "Thank you,<br>"
                + "Fool!sh Fashion Store";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(email);
            helper.setSubject(subject);

            content = content.replace("[[name]]", userName);

            helper.setText(content, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mailSender.send(message);

        return newTempPassoword;
    }
}
