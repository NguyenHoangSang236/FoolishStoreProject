package com.backend.core.entity.dto;

import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.enums.FilterTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class FilterFactory {
    public static final FilterRequest getFilterRequest(FilterTypeEnum filterType) {
        switch (filterType) {
            case PRODUCT : return new ProductFilterRequestDTO();
        }

        return null;
    }
}
