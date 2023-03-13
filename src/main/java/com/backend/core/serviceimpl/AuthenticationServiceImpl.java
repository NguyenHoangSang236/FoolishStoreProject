package com.backend.core.serviceimpl;

import com.backend.core.entity.Account;
import com.backend.core.entity.Customer;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entity.renderdto.StaffRenderInfoDTO;
import com.backend.core.repository.*;
import com.backend.core.service.AuthenticationService;
import com.backend.core.util.CheckUtils;
import com.backend.core.util.EnumsList;
import com.backend.core.util.ExceptionHandlerUtils;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

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



    @Override
    public ApiResponse loginIntoSystem(Account account, HttpSession session) {
        Account loginAcc = new Account();
        StaffRenderInfoDTO staffInfo = new StaffRenderInfoDTO();
        CustomerRenderInfoDTO customerInfo = new CustomerRenderInfoDTO();

        try{
            loginAcc = accountRepo.getAccountByUserNameAndPassword(account.getUserName(), account.getPassword());

            if(loginAcc != null) {
                session.setAttribute("currentUser", loginAcc);

                if(loginAcc.getRole().equals(EnumsList.ADMIN.name()) || loginAcc.getRole().equals(EnumsList.SHIPPER.name())) {
                    staffInfo = staffRenderInfoRepo.getStaffInfoByUserNameAndPassword(account.getUserName(), account.getPassword());
                    return new ApiResponse("success", staffInfo);
                }
                else if(loginAcc.getRole().equals(EnumsList.CUSTOMER.name())) {
                    customerInfo = customerRenderInfoRepo.getCustomerInfoByUserNameAndPassword(account.getUserName(), account.getPassword());
                    return new ApiResponse("success", customerInfo);
                }
                else return new ApiResponse("failed", "This role is not existed");
            }
            else return new ApiResponse("failed", "This account is not existed");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", e.toString());
        }
    }


    @Override
    public ApiResponse forgotPassword(Account account) {
        return null;
    }


    @Override
    public ApiResponse logoutFromSystem(HttpSession session) {
        try {
            session.removeAttribute("currentUser");
            return new ApiResponse("success", "Logout successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", e.toString());
        }
    }


    @Override
    public ApiResponse registerNewAccount(Account accountFromUI, BindingResult bindingResult) {
        accountFromUI.setRole("CUSTOMER");
        Customer customer = accountFromUI.getCustomer();

        try{
            //check for null information
            if(bindingResult.hasErrors()) {
                return new ApiResponse("failed", ExceptionHandlerUtils.getHandleBindException(new BindException(bindingResult)));
            }
            //check valid username and password
            else if(!CheckUtils.checkValidStringType(accountFromUI.getUserName(), EnumsList.HAS_NO_SPACE) ||
                    !CheckUtils.checkValidStringType(accountFromUI.getPassword(), EnumsList.HAS_NO_SPACE)) {
                return new ApiResponse("failed", "Please remove all spaces in Username and Password !!");
            }
            //check for existed username
            else if (accountRepo.getAccountByUserName(accountFromUI.getUserName()) != null) {
                return new ApiResponse("failed", "This username has already been existed");
            }
            //check valid phone number
//            else if (!CheckUtils.isValidPhoneNumber(customer.getPhoneNumber())) {
//                return "This is not a phone number !!";
//            }
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
            return new ApiResponse("failed", e.toString());
        }
    }
}
