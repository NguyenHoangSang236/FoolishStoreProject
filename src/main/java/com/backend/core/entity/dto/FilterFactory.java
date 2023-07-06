package com.backend.core.entity.dto;

import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.enums.FilterTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class FilterFactory {
    public static final FilterRequest getFilterRequest(FilterTypeEnum filterType) {
        return switch (filterType) {
            case PRODUCT -> new ProductFilterRequestDTO();
            case INVOICE -> new InvoiceFilterRequestDTO();
            case CART_ITEMS -> new CartItemFilterRequestDTO();
            default -> null;
        };
    }
}
