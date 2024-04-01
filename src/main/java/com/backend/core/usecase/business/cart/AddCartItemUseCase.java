package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.CartItemDTO;
import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.CartEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AddCartItemUseCase extends UseCase<AddCartItemUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    CustomerRepository customerRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        CartItemDTO cartItemDTO = input.getCartItem();
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        Cart newCartItem;

        Integer pmId = productManagementRepo.getProductsManagementIdByProductIDAndColorAndSize(
                cartItemDTO.getProductId(),
                cartItemDTO.getColor(),
                cartItemDTO.getSize());

        if (pmId == null) {
            return new ApiResponse("failed", "This product does not exist", HttpStatus.BAD_REQUEST);
        } else {
            ProductManagement productManagement = productManagementRepo.getProductManagementById(pmId);

            System.out.println(productManagement.getId());

            //check available product available quantity is higher than ordered quantity or not
            if (productManagement.getAvailableQuantity() >= cartItemDTO.getQuantity()) {
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
                            CartEnum.NOT_BOUGHT_YET.name(),
                            0
                    );
                    cartRepo.save(newCartItem);
                }
                return new ApiResponse("success", "Add successfully", HttpStatus.OK);
            } else {
                return new ApiResponse("failed", "Do not have enough quantity for your order", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Value
    public static class InputValue implements InputValues {
        CartItemDTO cartItem;
        HttpServletRequest httpRequest;
    }
}
