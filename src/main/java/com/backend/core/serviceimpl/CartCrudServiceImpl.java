package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.entity.dto.ListRequestDTO;
import com.backend.core.entity.dto.PaginationDTO;
import com.backend.core.entity.renderdto.CartRenderInfoDTO;
import com.backend.core.entity.tableentity.Cart;
import com.backend.core.entity.tableentity.ProductManagement;
import com.backend.core.enums.CartBuyingStatusEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.*;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("CartCrudServiceImpl")
public class CartCrudServiceImpl implements CrudService {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    ProductManagementRepository productRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;


    public CartCrudServiceImpl() {}

    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
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

                //check available product available quantity is higher than ordered quantity or not
                if(productManagement.getAvailableQuantity() >= cartItemDTO.getQuantity()) {
                    // get total quantity of the selected product in customer cart
                    int count = cartRepo.getExistedCartItemCountByCustomerIdAndProductManagementId(customerId, productManagement.getId());

                    // if it exists in the customer cart -> add its quantity
                    if (count > 0) {
                        newCartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(productManagement.getId(), customerId);
                        newCartItem.addQuantity(cartItemDTO.getQuantity());
                        cartRepo.save(newCartItem);
                    }
                    // if it does not exist in the customer cart -> create new one
                    else {
                        newCartItem = new Cart(
                                customerRepo.getCustomerById(customerId),
                                productManagement,
                                cartItemDTO.getQuantity(),
                                CartBuyingStatusEnum.NOT_BOUGHT_YET.name(),
                                0
                        );
                        cartRepo.save(newCartItem);

//                        productManagement.subtractQuantity(cartItemDTO.getQuantity());
//                        productManagementRepo.save(productManagement);
                    }
                    return new ApiResponse("success", "Add successfully");
                } else {
                    return new ApiResponse("failed", "Do not have enough quantity for your order");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }

    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse removingResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                ListRequestDTO listRequestDTO = (ListRequestDTO) paramObj;

                int[] selectedCartIdArr = listRequestDTO.getIntegerArray();

                if(selectedCartIdArr != null) {
                    for (int id: selectedCartIdArr) {
                        Cart cart = cartRepo.getCartById(id);

                        if(cart.getCustomer().getId() == customerId && cart.getBuyingStatus().equals(CartBuyingStatusEnum.NOT_BOUGHT_YET.name())) {
                            customQueryRepo.deleteCartById(id);
                        }
                        else return new ApiResponse("failed", "This cart item is not yours");
                    }
                    return new ApiResponse("success", "Remove successfully");
                }
                else return new ApiResponse("failed", "Choose items to delete first");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse updatingResponse(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                List<Object> cartItemList = listRequestDTO.getObjectList();

                if(cartItemList != null) {
                    for(Object obj: cartItemList) {
                        // parse json string without any double quotation marks to an object
                        Gson gson = new Gson();
                        CartItemDTO cartItemDTO = gson.fromJson(obj.toString(), CartItemDTO.class);

                        //update cart by cartDTO
                        Cart cartItm = cartRepo.getCartById(cartItemDTO.getCartId());

                        int pmId = productManagementRepo.getPrductsManagementIdByProductIDAndColorAndSize(
                                cartItemDTO.getProductId(),
                                cartItemDTO.getColor(),
                                cartItemDTO.getSize());

                        ProductManagement pm = productManagementRepo.getProductManagementById(pmId);

                        //check if updated cart quantity is higher than product's available quantity or not
                        if(pm.getAvailableQuantity() < cartItemDTO.getQuantity()) {
                            return new ApiResponse("failed", "We only have " + pm.getAvailableQuantity() + " items left!");
                        }
                        else {
                            Cart existedCartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(pmId, customerId);

                            // if the selected editing information of a cart item does not match with any others in the cart -> update information for that item
                            if(existedCartItem == null) {
                                pm = productManagementRepo.getProductManagementById(pmId);

                                //check if updated cart quantity is higher than product's available quantity or not
                                if(pm.getAvailableQuantity() < cartItemDTO.getQuantity()) {
                                    return new ApiResponse("failed", "We only have " + pm.getAvailableQuantity() + " items left!");
                                }
                                else {
                                    cartItm.setQuantity(cartItemDTO.getQuantity());
                                    cartItm.setProductManagement(pm);

                                    cartRepo.save(cartItm);
                                }
                            }
                            // if the updating information of a cart item matches with one in the cart -> add quantity for the matched one -> remove the update-requested cart item
                            else if(existedCartItem != null && existedCartItem.getId() != cartItemDTO.getCartId()){
                                existedCartItem.addQuantity(cartItemDTO.getQuantity());
                                cartRepo.save(existedCartItem);
                                customQueryRepo.deleteCartById(cartItemDTO.getCartId());
                            }
                            // if the updating information of a cart item does not change in product detail (change quantity or select status only)
                            else if(existedCartItem != null && existedCartItem.getId() == cartItemDTO.getCartId()) {
                                existedCartItem.setQuantity(cartItemDTO.getQuantity());
                                existedCartItem.setSelectStatus(cartItemDTO.getSelectStatus());
                                cartRepo.save(existedCartItem);
                            }
                        }
                    }

                    return new ApiResponse("success", "Update cart items successfully");
                }
                else return new ApiResponse("failed", "Please select items in cart to update");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
        List<CartRenderInfoDTO> cartItemList = new ArrayList<>();

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            if(paramObj instanceof PaginationDTO) {
                try {
                    PaginationDTO pagination = (PaginationDTO) paramObj;

                    cartItemList = cartRenderInfoRepo.getFullCartListByCustomerId(
                            customerId,
                            (pagination.getPage() - 1) * pagination.getLimit(),
                            pagination.getLimit()
                    );
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
                }
            }
        }

        return new ApiResponse("success", cartItemList);
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {

        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        int totalQuantity = 0;

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                totalQuantity = cartRepo.getCartQuantityByCustomerId(customerId);
            }
            catch (Exception e) {
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
        return  new ApiResponse("success", totalQuantity);
    }


    @Override
    public ApiResponse readingById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }
}
