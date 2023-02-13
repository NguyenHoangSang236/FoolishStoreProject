package com.backend.core.controller;

import com.backend.core.entity.Account;
import com.backend.core.entity.Cart;
import com.backend.core.entity.Customer;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.repository.CartRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartPage {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CustomerRepository customerRepo;


    @GetMapping("/showFullCart")
    public ApiResponse getCartByCustomerId(HttpSession session) {
        List<Cart> cartList;
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                Customer testCus = customerRepo.getCustomerById(customerId);

                if(testCus != null) {
                    cartList = cartRepo.getCurrentCartByCustomerId(customerId);
                    return new ApiResponse("success", cartList);
                }
                else return new ApiResponse("failed", "This customer ID is not existed");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", e.toString());
            }
        }
    }


    @PostMapping("/remove")
    public ApiResponse updateCart(@RequestBody int[] selectedCartIdArr, HttpSession session) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                if(selectedCartIdArr.length > 0) {
                    for (int id: selectedCartIdArr) {
                        cartRepo.deleteById(id);
                    }
                    return new ApiResponse("success", "Remove successfully");
                }
                else return new ApiResponse("failed", "Choose items to delete first");

            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", e.toString());
            }
        }
    }
}
