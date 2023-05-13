package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.CartItemDTO;
import com.backend.core.entity.dto.PaginationDTO;
import com.backend.core.entity.renderdto.CartRenderInfoDTO;
import com.backend.core.entity.tableentity.Cart;
import com.backend.core.entity.tableentity.ProductManagement;
import com.backend.core.enums.CartBuyingStatusEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.CartRenderInfoRepository;
import com.backend.core.repository.CartRepository;
import com.backend.core.repository.CustomerRepository;
import com.backend.core.repository.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
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
                                cartItemDTO.getQuantity()
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

                        if(cart.getCustomer().getId() == customerId) {
                            cartRepo.deleteById(id);
                        }
                        else if(cart.getBuyingStatus().equals(CartBuyingStatusEnum.NOT_BOUGHT_YET.name())) {
                            return new ApiResponse("failed", "This cart item was bought");
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
    public ApiResponse updatingResponse(List<Object> paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                if(paramObj.size() > 0) {
                    for(Object obj: paramObj) {
                        CartItemDTO cartItemDTO = new ObjectMapper().convertValue(obj, CartItemDTO.class);

                        //update cart by cartDTO
                        Cart cartItm = cartRepo.getCartById(cartItemDTO.getCartId());

                        //check if the right account updates cart or not
                        if(cartItm.getCustomer().getId() == customerId) {
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
                                cartItm.setQuantity(cartItemDTO.getQuantity());
                                cartItm.setProductManagement(pm);

                                cartRepo.save(cartItm);
                            }
                        }
                        else {
                            return new ApiResponse("failed", "You are not the owner!");
                        }
                    }
                    return new ApiResponse("success", "Update cart items successfully");
                }
                else return new ApiResponse("failed", "Please select items in cart to update");
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        String sessionId = getSessionIdFromCookie(httpRequest.getCookies());

        System.out.println("from request: " + sessionId);
        System.out.println("from sesion: " + session.getId());

        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        List<CartRenderInfoDTO> cartItemList = new ArrayList<>();

//        Enumeration<String> attributes = session.getAttributeNames();
//        while (attributes.hasMoreElements()) {
//            String attribute = (String) attributes.nextElement();
//            System.out.println(attribute+" : "+session.getAttribute(attribute));
//        }

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
        return  null;
//        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
//
//        if(customerId == 0) {
//            return new ApiResponse("failed", "Login first");
//        }
//        else {
//            if(renderType == "ALL_CART_ITEMS") {
//                List<CartRenderInfoDTO> cartList = cartRenderInfoRepo.getFullCartListByCustomerId(customerId);
//
//                return new ApiResponse("success", cartList);
//            }
//        }
//        return new ApiResponse("failed", "Wrong render type");
    }


    @Override
    public ApiResponse readingById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    public String getSessionIdFromCookie(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + ":" + cookie.getValue());
            if ("JSESSIONID".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
