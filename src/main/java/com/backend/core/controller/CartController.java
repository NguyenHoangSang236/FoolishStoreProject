package com.backend.core.controller;

import com.backend.core.entity.Cart;
import com.backend.core.entity.Customer;
import com.backend.core.entity.ProductManagement;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.repository.CartRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.repository.ProductManagementRepository;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;


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
                        Cart cart = cartRepo.getCartById(id);

                        if(cart.getCustomer().getId() == customerId && cart.getBuyingStatus() == 0) {
                            cartRepo.deleteById(id);
                        }
                        else return new ApiResponse("failed", "This cart item is not yours");
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


    @PostMapping("/add")
    public ApiResponse addNewProducttoCart(@RequestBody CartItemDTO cartItemDTO, HttpSession session) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
        Cart newCartItem;
        ProductManagement productManagement;

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try{
                int pmId = productManagementRepo.getPrductsManagementIdByProductIDAndColorAndSize(
                        cartItemDTO.getProductId(),
                        cartItemDTO.getColor(),
                        cartItemDTO.getSize());

                productManagement = productManagementRepo.getProductManagementById(pmId);

                //check available product quantity is higher than order quantity or not
                if(productManagement.getAvailableQuantity() >= cartItemDTO.getQuantity()) {
                    int count = cartRepo.getExistedCartItemCountByProductIdAndColorAndSize(customerId,productManagement.getId());

                    if (count > 0) {
                        newCartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(productManagement.getId(), customerId);
                        newCartItem.addQuantity(cartItemDTO.getQuantity());
                        cartRepo.save(newCartItem);
                    }
                    else {
                        newCartItem = new Cart(
                                customerRepo.getCustomerById(customerId),
                                productManagement,
                                cartItemDTO.getQuantity(),
                                0
                        );
                        cartRepo.save(newCartItem);

                        productManagement.subtractQuantity(cartItemDTO.getQuantity());
                        productManagementRepo.save(productManagement);
                    }
                    return new ApiResponse("success", "Add successfully");
                } else {
                    return new ApiResponse("failed", "Do not have enough quantity for your order");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", e.toString());
            }
        }
    }
}