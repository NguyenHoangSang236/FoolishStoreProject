package com.backend.core.entity.cart.gateway;

import com.backend.core.entity.FilterRequest;
import com.backend.core.entity.api.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashMap;

@Getter
@Setter
public class CartItemFilterRequestDTO implements FilterRequest, Serializable {
    @JsonProperty("filter")
    CartItemFilterDTO filter;

    @JsonProperty("pagination")
    PaginationDTO pagination;


    public CartItemFilterRequestDTO(CartItemFilterDTO filter, PaginationDTO pagination) {
        this.filter = filter;
        this.pagination = pagination;
    }

    public CartItemFilterRequestDTO() {
    }


    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public void setFilter(Object filter) {
        LinkedHashMap<String, Object> filterMap = (LinkedHashMap<String, Object>) filter;
        ObjectMapper objectMapper = new ObjectMapper();
        this.filter = objectMapper.convertValue(filterMap, CartItemFilterDTO.class);
    }

    @Override
    public PaginationDTO getPagination() {
        return this.pagination;
    }

    @Override
    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}
