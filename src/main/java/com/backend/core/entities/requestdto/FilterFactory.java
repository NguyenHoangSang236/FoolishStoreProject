package com.backend.core.entities.requestdto;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.account.AccountFilterRequestDTO;
import com.backend.core.entities.requestdto.cart.CartItemFilterRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryFilterRequestDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.requestdto.product.ProductFilterRequestDTO;
import com.backend.core.enums.FilterTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class FilterFactory {
    public static final FilterRequest getFilterRequest(FilterTypeEnum filterType) {
        return switch (filterType) {
            case PRODUCT -> new ProductFilterRequestDTO();
            case INVOICE -> new InvoiceFilterRequestDTO();
            case CART_ITEMS -> new CartItemFilterRequestDTO();
            case COMMENT -> new CommentFilterRequestDTO();
            case ACCOUNT -> new AccountFilterRequestDTO();
            case DELIVERY -> new DeliveryFilterRequestDTO();
            default -> null;
        };
    }
}
