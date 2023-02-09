package com.backend.core.service;

import com.backend.core.entity.Account;
import com.backend.core.entity.Customer;
import com.backend.core.entity.Staff;
import com.backend.core.repository.AccountRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    StaffRepository staffRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    AccountRepository accountRepo;


    //get login account
    public Account getAccountByUsernameAndPassword(String username, String password) {
        return accountRepo.getAccountByUserNameAndPassword(username, password);
    }

    //get staff by account
    public Staff getStaffByUserAccount(Account acc) {
        return staffRepo.getStaffByAccountId(acc.getId());
    }

    //get customer by account
    public Customer getCustomerByUserAccount(Account acc) {
        return customerRepo.getCustomerById(acc.getId());
    }
}
