package com.backend.core.serviceimpl;

import com.backend.core.entity.Cart;
import com.backend.core.entity.ProductManagement;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.entity.renderdto.CartRenderInfoDTO;
import com.backend.core.repository.*;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartCrudServiceImpl implements CrudService {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ProductManagementRepository productRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;


    @Override
    public ApiResponse creationalResponse(Object paramObj, HttpSession session) {
        CartItemDTO cartItemDTO = (CartItemDTO) paramObj;
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
                    int count = cartRepo.getExistedCartItemCountByCustomerIdAndProductManagementId(customerId,productManagement.getId());

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


    @Override
    public ApiResponse removingResponse(Object paramObj, HttpSession session) {
        int[] selectedCartIdArr = (int[]) paramObj;

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


    @Override
    public ApiResponse updatingResponse(List<Object> paramObj, HttpSession session) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                if(paramObj.size() > 0) {
                    for(Object obj: paramObj) {
                        System.out.println(obj);

                        CartItemDTO cartItemDTO = new ObjectMapper().convertValue(obj, CartItemDTO.class);

                        //update cart by cartDTO
                        Cart cartItm = cartRepo.getCartById(cartItemDTO.getCartId());
                        int pmId = productManagementRepo.getPrductsManagementIdByProductIDAndColorAndSize(
                                cartItemDTO.getProductId(),
                                cartItemDTO.getColor(),
                                cartItemDTO.getSize());
                        ProductManagement pm = productManagementRepo.getProductManagementById(pmId);

                        cartItm.setQuantity(cartItemDTO.getQuantity());
                        cartItm.setProductManagement(pm);

                        cartRepo.save(cartItm);
                    }

                    return new ApiResponse("success", "Update cart items successfully");
                }
                else return new ApiResponse("failed", "Please select items in cart to update");
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", e.toString());
            }
        }
    }


    @Override
    public ApiResponse readingSinglePostingResponse(Object paramObj, HttpSession session) {

        return null;
    }


    @Override
    public ApiResponse readingListPostingReponse(List<Object> paramObjList, HttpSession session) {

        return null;
    }


    @Override
    public ApiResponse readingListGettingReponse(HttpSession session) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            List<CartRenderInfoDTO> cartList = cartRenderInfoRepo.getFullCartListByCustomerId(customerId);

            return new ApiResponse("success", cartList);
        }
    }
}
