package com.backend.core.entity;

import com.backend.core.entity.account.gateway.AccountFilterRequestDTO;
import com.backend.core.entity.cart.gateway.CartItemFilterRequestDTO;
import com.backend.core.entity.comment.gateway.CommentFilterRequestDTO;
import com.backend.core.entity.delivery.gateway.DeliveryFilterRequestDTO;
import com.backend.core.entity.invoice.gateway.InvoiceFilterRequestDTO;
import com.backend.core.entity.notification.gateway.NotificationFilterRequestDTO;
import com.backend.core.entity.product.gateway.ProductFilterRequestDTO;
import com.backend.core.entity.refund.gateway.RefundFilterRequestDTO;
import com.backend.core.usecase.statics.FilterTypeEnum;
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
            case NOTIFICATION -> new NotificationFilterRequestDTO();
            case REFUND -> new RefundFilterRequestDTO();
            default -> null;
        };
    }
}
