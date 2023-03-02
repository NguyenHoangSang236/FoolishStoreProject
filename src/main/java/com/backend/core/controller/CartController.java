package com.backend.core.controller;

import com.backend.core.abstractclasses.Controller;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.repository.CartRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.repository.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.backend.core.serviceimpl.CartCrudServiceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/cart")
public class CartController extends Controller {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;


    public CartController(CrudService crudService) {
        super(crudService);
    }


//    @GetMapping("/showFullCart")
//    public ApiResponse getCartByCustomerId(HttpSession session) {
//        List<Cart> cartList;
//        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
//
//        if(customerId == 0) {
//            return new ApiResponse("failed", "Login first");
//        }
//        else {
//            try {
//                Customer testCus = customerRepo.getCustomerById(customerId);
//
//                if(testCus != null) {
//                    cartList = cartRepo.getCurrentCartByCustomerId(customerId);
//                    return new ApiResponse("success", cartList);
//                }
//                else return new ApiResponse("failed", "This customer ID is not existed");
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                return new ApiResponse("failed", e.toString());
//            }
//        }
//    }
//
//
//    @PostMapping("/remove")
//    public ApiResponse updateCart(@RequestBody int[] selectedCartIdArr, HttpSession session) {
//        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
//
//        if(customerId == 0) {
//            return new ApiResponse("failed", "Login first");
//        }
//        else {
//            try {
//                if(selectedCartIdArr.length > 0) {
//                    for (int id: selectedCartIdArr) {
//                        Cart cart = cartRepo.getCartById(id);
//
//                        if(cart.getCustomer().getId() == customerId && cart.getBuyingStatus() == 0) {
//                            cartRepo.deleteById(id);
//                        }
//                        else return new ApiResponse("failed", "This cart item is not yours");
//                    }
//                    return new ApiResponse("success", "Remove successfully");
//                }
//                else return new ApiResponse("failed", "Choose items to delete first");
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                return new ApiResponse("failed", e.toString());
//            }
//        }
//    }


//    @PostMapping("/add")
//    public ApiResponse addNewProductToCart(@RequestBody CartItemDTO cartItemDTO, HttpSession session) {
//        
//    }

    @PostMapping("/add")
    @Override
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CartItemDTO cartItemDTO = objectMapper.readValue(json, CartItemDTO.class);;

        return crudService.createResponse(cartItemDTO, session);
    }

//    @Override
//    public ApiResponse deleteSelectedItemById(int id) {
//
//    }
//
//    @Override
//    public ApiResponse updateSelectedItemById(int id) {
//
//    }
//
//    @Override
//    public ApiResponse getListOfItems() {
//        return null;
//    }
//
//    @Override
//    public ApiResponse getItem() {
//        return null;
//    }
}