package com.backend.core.service;

import com.backend.core.entity.Account;
import com.backend.core.entity.Customer;
import com.backend.core.repository.AccountInsertRepository;
import com.backend.core.repository.AccountRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.util.CheckUtils;
import com.backend.core.util.EnumsList;
import com.backend.core.util.ExceptionHandlerUtils;
import com.backend.core.util.ValueRenderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Service
public class RegisterService {
    @Autowired
    AccountInsertRepository accountInsertRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    AccountRepository accountRepo;

    public String registerNewAccount(Account accountFromUI, BindingResult bindingResult)  throws BindException {
        Customer customer = accountFromUI.getCustomer();

        try{
            //check for null information
            if(bindingResult.hasErrors()) {
                return ExceptionHandlerUtils.getHandleBindException(new BindException(bindingResult));
            }
            //check valid username and password
            else if(!CheckUtils.checkValidStringType(accountFromUI.getUserName(), EnumsList.HAS_NO_SPACE)
                    || !CheckUtils.checkValidStringType(accountFromUI.getPassword(), EnumsList.HAS_NO_SPACE)) {
                return "Please remove all spaces in Username and Password !!";
            }
            //check for existed username
            else if (accountRepo.getAccountByUserName(accountFromUI.getUserName()) != null) {
                return "This username has already been existed";
            }
            //check valid phone number
//            else if (!CheckUtils.isValidPhoneNumber(customer.getPhoneNumber())) {
//                return "This is not a phone number !!";
//            }
            //check valid email
            else if (!CheckUtils.isValidEmail(customer.getEmail())) {
                return "This is not an email, please input again !!";
            }
            //check reused email
            else if (customerRepo.getCustomerByEmail(customer.getEmail()) != null) {
                return "This email has been used !!";
            }
            //check valid customer full name
            else if (CheckUtils.hasSpecialSign(customer.getName())) {
                return "Full name can not have special signs !!";
            }
            else{
                accountFromUI.setCustomer(null);
                accountRepo.save(accountFromUI);

                customer.setAccount(accountFromUI);
                String formattedName = ValueRenderUtils.formattedPersonFullName(customer.getName());
                customer.setName(formattedName);
                customerRepo.save(customer);

                return "Register successfully";
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
    }
}
