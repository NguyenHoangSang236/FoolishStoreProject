package com.backend.core.serviceimpl;

import com.backend.core.entity.tableentity.Account;
import com.backend.core.entity.tableentity.Customer;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entity.renderdto.StaffRenderInfoDTO;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.StringTypeEnum;
import com.backend.core.repository.*;
import com.backend.core.service.AuthenticationService;
import com.backend.core.util.CheckUtils;
import com.backend.core.enums.RoleEnum;
import com.backend.core.util.ExceptionHandlerUtils;
import com.backend.core.util.ValueRenderUtils;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
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




    @Override
    public ResponseEntity<ApiResponse> loginIntoSystem(Account account, HttpSession session) throws URISyntaxException {
        Account loginAcc = new Account();
        StaffRenderInfoDTO staffInfo = new StaffRenderInfoDTO();
        CustomerRenderInfoDTO customerInfo = new CustomerRenderInfoDTO();

        URI location = new URI("http://192.168.1.9:8080/systemAuthentication/login");
        HttpHeaders responseHeaders = new HttpHeaders();

        try{
            loginAcc = accountRepo.getAccountByUserNameAndPassword(account.getUserName(), account.getPassword());

            if(loginAcc != null) {
                session.setAttribute("currentUser", loginAcc);

                responseHeaders.setLocation(location);
                responseHeaders.set("sessionid", session.getId());
//                Account currentUser = (Account) session.getAttribute("currentUser");
//
//                System.out.println(currentUser.getUserName());

//                Enumeration<String> attributes = session.getAttributeNames();
//                while (attributes.hasMoreElements()) {
//                    String attribute = (String) attributes.nextElement();
//                    System.out.println(attribute+" : "+session.getAttribute(attribute));
//                }

                if(loginAcc.getRole().equals(RoleEnum.ADMIN.name()) || loginAcc.getRole().equals(RoleEnum.SHIPPER.name())) {
                    staffInfo = staffRenderInfoRepo.getStaffInfoByUserNameAndPassword(account.getUserName(), account.getPassword());
                    return new ResponseEntity<>(new ApiResponse("success", staffInfo), responseHeaders, HttpStatus.OK);
                }
                else if(loginAcc.getRole().equals(RoleEnum.CUSTOMER.name())) {
                    customerInfo = customerRenderInfoRepo.getCustomerInfoByUserNameAndPassword(account.getUserName(), account.getPassword());
                    return new ResponseEntity<>(new ApiResponse("success", customerInfo), responseHeaders, HttpStatus.OK);
                }
                else return new ResponseEntity<>(new ApiResponse("failed", "This role is not existed"), responseHeaders, HttpStatus.BAD_REQUEST);

            }
            else return new ResponseEntity<>(new ApiResponse("failed", "This role is not existed"), responseHeaders, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> forgotPassword(String username, String email) throws URISyntaxException {
        URI location = new URI("http://192.168.1.9:8080/systemAuthentication/forgotPassword");
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Account currentAccount = accountRepo.getAccountByUserName(username);

            if(currentAccount != null) {
                if(currentAccount.getCustomer().getEmail().equals(email)) {
                    String newPassword = getNewTemporaryRandomPasswordMail(username, email);

                    currentAccount.setPassword(newPassword);
                    accountRepo.save(currentAccount);
                }
                else return new ResponseEntity<>(new ApiResponse("failed", "This email does not belong to this account"), responseHeaders, HttpStatus.BAD_REQUEST);
            }
            else return new ResponseEntity<>(new ApiResponse("failed", "This account is not existed"), responseHeaders, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), responseHeaders, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse("success", "Change password successfully"), responseHeaders, HttpStatus.OK);
    }


    @Override
    public ApiResponse logoutFromSystem(HttpSession session) {
        try {
            session.removeAttribute("currentUser");
            return new ApiResponse("success", "Logout successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }


    @Override
    public ApiResponse registerNewAccount(Account accountFromUI, BindingResult bindingResult) {
        accountFromUI.setRole("CUSTOMER");
        Customer customer = accountFromUI.getCustomer();
        customer.setImage("1YduwQYRoEMMFWsgCN4ZFhH1GEfzw4uUt");

        try{
            //check for null information
            if(bindingResult.hasErrors()) {
                return new ApiResponse("failed", ExceptionHandlerUtils.getHandleBindException(new BindException(bindingResult)));
            }
            //check for null password
            else if(accountFromUI.getPassword().isEmpty() || accountFromUI.getPassword().isBlank()) {
                return new ApiResponse("failed", "Password can not be null !!");
            }
            //check valid username and password
            else if(!CheckUtils.checkValidStringType(accountFromUI.getUserName(), StringTypeEnum.HAS_NO_SPACE) ||
                    !CheckUtils.checkValidStringType(accountFromUI.getPassword(), StringTypeEnum.HAS_NO_SPACE)) {
                return new ApiResponse("failed", "Please remove all spaces in Username and Password !!");
            }
            //check for existed username
            else if (accountRepo.getAccountByUserName(accountFromUI.getUserName()) != null) {
                return new ApiResponse("failed", "This username has already been existed");
            }
//            //check valid phone number
//            else if (!CheckUtils.isValidPhoneNumber(customer.getPhoneNumber())) {
//                return "This is not a phone number !!";
//            }
            //check valid VN phone number
            else if (customer.getPhoneNumber().length() != 10) {
                return new ApiResponse("failed", "Phone number must be 10-digit !!");
            }
            //check valid email
            else if (!CheckUtils.isValidEmail(customer.getEmail())) {
                return new ApiResponse("failed", "This is not an email, please input again !!");
            }
            //check reused email
            else if (customerRepo.getCustomerByEmail(customer.getEmail()) != null) {
                return new ApiResponse("failed", "This email has been used !!");
            }
            //check valid customer full name
            else if (CheckUtils.hasSpecialSign(customer.getName())) {
                return new ApiResponse("failed", "Full name can not have special signs !!");
            }
            else{
                accountFromUI.setCustomer(null);
                accountRepo.save(accountFromUI);

                customer.setAccount(accountFromUI);
                String formattedName = ValueRenderUtils.formattedPersonFullName(customer.getName());
                customer.setName(formattedName);
                customerRepo.save(customer);

                return new ApiResponse("success", "Register successfully");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }


    public String getNewTemporaryRandomPasswordMail(String userName, String email) {
        String fromAddress = "nguyenhoangsang236@gmail.com";
        String senderName = "Fool!st Fashion Store";
        String subject = "Your new temporary password";
        String newTempPassoword = ValueRenderUtils.randomTemporaryPassword(userName);
        String content =  "Dear [[name]],<br>"
                        + "Your new temporary password is " + newTempPassoword + "<br>"
                        + "Please rememder to change a new password for your new account because this temporary password will be changed after you close the website !!<br><br>"
                        + "Thank you,<br>"
                        + "Fool!st Fashion Store";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);helper.setTo(email);
            helper.setSubject(subject);

            content = content.replace("[[name]]", userName);

            helper.setText(content, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        mailSender.send(message);

        return newTempPassoword;
    }
}
