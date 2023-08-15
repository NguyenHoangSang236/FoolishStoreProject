package com.backend.core.serviceImpl.common;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entities.renderdto.StaffRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.tableentity.Customer;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.RoleEnum;
import com.backend.core.enums.StringTypeEnum;
import com.backend.core.repository.account.AccountRepository;
import com.backend.core.repository.customer.CustomerRenderInfoRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.staff.StaffRenderInfoRepository;
import com.backend.core.repository.staff.StaffRepository;
import com.backend.core.service.AuthenticationService;
import com.backend.core.service.GoogleDriveService;
import com.backend.core.util.handler.BindExceptionHandler;
import com.backend.core.util.process.CheckUtils;
import com.backend.core.util.process.JwtUtils;
import com.backend.core.util.process.ValueRenderUtils;
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

import java.net.URI;
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

            if (loginAcc != null) {
                jwt = jwtUtils.generateJwt(loginAcc);
                loginAcc.setCurrentJwt(jwt);
                accountRepo.save(loginAcc);

                if (loginAcc.getRole().equals(RoleEnum.ADMIN.name()) || loginAcc.getRole().equals(RoleEnum.SHIPPER.name())) {
                    staffInfo = staffRenderInfoRepo.getStaffInfoByUserName(account.getUsername());

                    return new ResponseEntity<>(new ApiResponse("success", staffInfo, jwt), HttpStatus.OK);
                } else if (loginAcc.getRole().equals(RoleEnum.CUSTOMER.name())) {
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
        URI location = new URI("http://192.168.1.9:8080/systemAuthentication/forgotPassword");
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Account currentAccount = accountRepo.getAccountByUserName(username);

            if (currentAccount != null) {
                if (currentAccount.getCustomer().getEmail().equals(email)) {
                    String newPassword = getNewTemporaryRandomPasswordMail(username, email);

                    currentAccount.setPassword(newPassword);
                    accountRepo.save(currentAccount);
                } else
                    return new ResponseEntity<>(new ApiResponse("failed", "This email does not belong to this account"), responseHeaders, HttpStatus.BAD_REQUEST);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", "This account is not existed"), responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), responseHeaders, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse("success", "Change password successfully"), responseHeaders, HttpStatus.OK);
    }


    @Override
    public ResponseEntity logoutFromSystem(HttpServletRequest request) {
        try {
            String jwt = jwtUtils.getJwtFromRequest(request);
            jwtUtils.expireJwt(jwt);

            return new ResponseEntity(new ApiResponse("success", "Logout successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity registerNewAccount(Account accountFromUI, BindingResult bindingResult) {
        accountFromUI.setRole(RoleEnum.CUSTOMER.name());
        Customer customer = accountFromUI.getCustomer();
        customer.setImage("1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R");

        try {
            //check for null information
            if (bindingResult.hasErrors()) {
                return new ResponseEntity(new ApiResponse("failed", BindExceptionHandler.getHandleBindException(new BindException(bindingResult))), HttpStatus.BAD_REQUEST);
            }
            //check for null password
            else if (accountFromUI.getPassword().isEmpty() || accountFromUI.getPassword().isBlank()) {
                return new ResponseEntity(new ApiResponse("failed", "Password can not be null !!"), HttpStatus.BAD_REQUEST);
            }
            //check valid username and password
            else if (!checkUtils.checkValidStringType(accountFromUI.getUsername(), StringTypeEnum.HAS_NO_SPACE) ||
                    !checkUtils.checkValidStringType(accountFromUI.getPassword(), StringTypeEnum.HAS_NO_SPACE)) {
                return new ResponseEntity(new ApiResponse("failed", "Please remove all spaces in Username and Password !!"), HttpStatus.BAD_REQUEST);
            }
            //check for existed username
            else if (accountRepo.getAccountByUserName(accountFromUI.getUsername()) != null) {
                return new ResponseEntity(new ApiResponse("failed", "This username has already been existed"), HttpStatus.BAD_REQUEST);
            }
//            //check valid phone number
//            else if (!checkUtils.isValidPhoneNumber(customer.getPhoneNumber())) {
//                return "This is not a phone number !!";
//            }
            //check valid VN phone number
            else if (customer.getPhoneNumber().length() != 10) {
                return new ResponseEntity(new ApiResponse("failed", "Phone number must be 10-digit !!"), HttpStatus.BAD_REQUEST);
            }
            //check valid email
            else if (!checkUtils.isValidEmail(customer.getEmail())) {
                return new ResponseEntity(new ApiResponse("failed", "This is not an email, please input again !!"), HttpStatus.BAD_REQUEST);
            }
            //check reused email
            else if (customerRepo.getCustomerByEmail(customer.getEmail()) != null) {
                return new ResponseEntity(new ApiResponse("failed", "This email has been used !!"), HttpStatus.BAD_REQUEST);
            }
            //check valid customer full name
            else if (checkUtils.hasSpecialSign(customer.getName())) {
                return new ResponseEntity(new ApiResponse("failed", "Full name can not have special signs !!"), HttpStatus.BAD_REQUEST);
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

                return new ResponseEntity(new ApiResponse("success", "Register successfully"), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity updateProfile(CustomerRenderInfoDTO customerInfo, HttpServletRequest request) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(request);

        try {
            Customer customer = customerRepo.getCustomerById(customerId);
            customer.setCustomerInfoFromRenderInfo(customerInfo);
            customerRepo.save(customer);

            return new ResponseEntity(new ApiResponse("success", "Update profile successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public String getNewTemporaryRandomPasswordMail(String userName, String email) {
        String fromAddress = "nguyenhoangsang236@gmail.com";
        String senderName = "Fool!st Fashion Store";
        String subject = "Your new temporary password";
        String newTempPassoword = valueRenderUtils.randomTemporaryPassword(userName);
        String content = "Dear [[name]],<br>"
                + "Your new temporary password is " + newTempPassoword + "<br>"
                + "Please rememder to change a new password for your new account because this temporary password will be changed after you close the website !!<br><br>"
                + "Thank you,<br>"
                + "Fool!st Fashion Store";

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
