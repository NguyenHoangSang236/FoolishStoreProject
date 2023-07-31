package com.backend.core.entities.dto;

import com.backend.core.entities.dto.cart.CartItemFilterRequestDTO;
import com.backend.core.entities.dto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.dto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.dto.product.ProductFilterRequestDTO;
import com.backend.core.entities.interfaces.FilterRequest;
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
            default -> null;
        };
    }
}
