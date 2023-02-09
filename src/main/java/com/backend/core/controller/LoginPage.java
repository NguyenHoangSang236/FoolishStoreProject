package com.backend.core.controller;

import com.backend.core.entity.Account;
import com.backend.core.entity.Customer;
import com.backend.core.entity.Staff;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginPage {
    @Autowired
    LoginService loginService;


    @PostMapping
    public ApiResponse loginIntoSystem (@Validated @RequestBody Account accountFromUI, HttpSession session) {
        Account loginAcc = new Account();
        String status = "failed";
        try{
            loginAcc = loginService.getAccountByUsernameAndPassword(accountFromUI.getUserName(), accountFromUI.getPassword());

            if(loginAcc.getUserName() != null) {
                Staff staff = loginService.getStaffByUserAccount(loginAcc);
                Customer customer = loginService.getCustomerByUserAccount(loginAcc);

                if(staff != null) {
                    session.setAttribute("currentUser", staff);
                    status = "success";
                }
                else {
                    session.setAttribute("currentUser", customer);
                    status = "success";
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        return new ApiResponse(status, loginAcc);
    }


    @PostMapping("/forgotPassword")
    public void forgotPassword() {

    }
}
