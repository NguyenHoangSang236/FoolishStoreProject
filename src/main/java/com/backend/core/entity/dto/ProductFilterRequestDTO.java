package com.backend.core.entity.dto;

import com.backend.core.entity.interfaces.FilterRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductFilterRequestDTO implements FilterRequest {
    ProductFilterDTO filter;

    String key;


    public ProductFilterRequestDTO(ProductFilterDTO filter, String key) {
        this.filter = filter;
        this.key = key;
    }

    public ProductFilterRequestDTO() {}

    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setFilter(Object filter) {
        this.filter = (ProductFilterDTO) filter;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }
}
