package com.backend.core.usecase.usecases.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.cart.gateway.CartItemDTO;
import com.backend.core.entity.cart.gateway.CartItemFilterRequestDTO;
import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCartUseCase extends UseCase<UpdateCartUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        List<Object> cartItemList = input.getListRequest().getObjectList();

        if (cartItemList != null) {
            for (Object obj : cartItemList) {
                // parse json string without any double quotation marks to an object
                Gson gson = new Gson();
                CartItemDTO cartItemDTO = gson.fromJson(obj.toString(), CartItemDTO.class);

                if (cartItemDTO.getQuantity() < 1) {
                    return new ApiResponse("failed", "Cart item quantity must be higher than 0", HttpStatus.BAD_REQUEST);
                }

                // get cart item by id
                Cart requestCartItem = cartRepo.getCartById(cartItemDTO.getCartId());

                int pmId = productManagementRepo.getProductsManagementIdByProductIDAndColorAndSize(
                        cartItemDTO.getProductId(),
                        cartItemDTO.getColor(),
                        cartItemDTO.getSize());

                ProductManagement pm = productManagementRepo.getProductManagementById(pmId);

                // check if updated cart quantity is higher than product's available quantity or not
                if (pm.getAvailableQuantity() < cartItemDTO.getQuantity()) {
                    return new ApiResponse("failed", "We only have " + pm.getAvailableQuantity() + " items left!", HttpStatus.BAD_REQUEST);
                } else {
                    Cart cartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(pmId, customerId);

                    // if the selected editing information of a cart item does not match with any others in the cart -> update information for that item
                    if (cartItem == null) {
                        pm = productManagementRepo.getProductManagementById(pmId);

                        requestCartItem.setQuantity(cartItemDTO.getQuantity());
                        requestCartItem.setProductManagement(pm);

                        cartRepo.save(requestCartItem);
                    }
                    // if the updating information of a cart item matches with one in the cart -> add quantity for the matched one -> remove the update-requested cart item
                    else if (cartItem.getId() != cartItemDTO.getCartId()) {
                        cartItem.addQuantity(cartItemDTO.getQuantity());
                        cartRepo.save(cartItem);
                        customQueryRepo.deleteCartById(cartItemDTO.getCartId());
                    }
                    // if the updating information of a cart item does not change in product detail (change quantity or select status only)
                    else {
                        cartItem.setQuantity(cartItemDTO.getQuantity());
                        cartItem.setSelectStatus(cartItemDTO.getSelectStatus());
                        cartRepo.save(cartItem);
                    }
                }
            }

            return new ApiResponse("success", "Update cart items successfully", HttpStatus.OK);
        } else
            return new ApiResponse("failed", "Please select items in cart to update", HttpStatus.BAD_REQUEST);
    }

    @Value
    public static class InputValue implements InputValues {
        ListRequestDTO listRequest;
        HttpServletRequest httpRequest;
    }
}
